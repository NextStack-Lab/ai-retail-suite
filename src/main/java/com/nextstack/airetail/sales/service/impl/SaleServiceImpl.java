package com.nextstack.airetail.sales.service.impl;

import com.nextstack.airetail.branch.entity.Branch;
import com.nextstack.airetail.branch.repository.BranchRepository;
import com.nextstack.airetail.common.dto.PageRequestDto;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.PageResponseMapper;
import com.nextstack.airetail.customer.entity.Customer;
import com.nextstack.airetail.customer.repository.CustomerRepository;
import com.nextstack.airetail.exception.BusinessException;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import com.nextstack.airetail.inventory.entity.Inventory;
import com.nextstack.airetail.inventory.repository.InventoryRepository;
import com.nextstack.airetail.product.entity.Product;
import com.nextstack.airetail.product.repository.ProductRepository;
import com.nextstack.airetail.sales.dto.request.SaleItemRequest;
import com.nextstack.airetail.sales.dto.request.SalePageRequest;
import com.nextstack.airetail.sales.dto.request.SaleRequest;
import com.nextstack.airetail.sales.dto.response.SaleResponse;
import com.nextstack.airetail.sales.entity.Sale;
import com.nextstack.airetail.sales.entity.SaleItem;
import com.nextstack.airetail.sales.entity.SaleStatus;
import com.nextstack.airetail.sales.mapper.SaleMapper;
import com.nextstack.airetail.sales.repository.SaleRepository;
import com.nextstack.airetail.sales.service.SaleService;
import com.nextstack.airetail.sales.specification.SaleSpecifications;
import com.nextstack.airetail.sales.validator.SaleValidator;
import com.nextstack.airetail.user.entity.User;
import com.nextstack.airetail.user.repository.UserRepository;
import com.nextstack.airetail.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of {@link SaleService}.
 */
@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    private final SaleMapper saleMapper;
    private final SaleValidator saleValidator;

    /**
     * Creates the service with required dependencies.
     */
    public SaleServiceImpl(SaleRepository saleRepository,
                           BranchRepository branchRepository,
                           CustomerRepository customerRepository,
                           ProductRepository productRepository,
                           InventoryRepository inventoryRepository,
                           UserRepository userRepository,
                           SaleMapper saleMapper,
                           SaleValidator saleValidator) {
        this.saleRepository = saleRepository;
        this.branchRepository = branchRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
        this.saleMapper = saleMapper;
        this.saleValidator = saleValidator;
    }

    @Override
    public SaleResponse create(SaleRequest request) {
        saleValidator.validateCreate(request);
        Branch branch = findBranch(request.getBranchId());
        User cashier = findCashier();
        Customer customer = findCustomerOptional(request.getCustomerId());

        Sale sale = Sale.builder()
                .saleNumber(generateSaleNumber())
                .saleDate(request.getSaleDate() != null ? request.getSaleDate() : Instant.now())
                .tax(defaultZero(request.getTax()))
                .discount(defaultZero(request.getDiscount()))
                .status(request.getStatus() != null ? request.getStatus() : SaleStatus.COMPLETED)
                .branch(branch)
                .customer(customer)
                .cashier(cashier)
                .items(new ArrayList<>())
                .build();
        sale.setActive(true);

        BigDecimal subtotal = BigDecimal.ZERO;
        for (SaleItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findByIdAndActiveTrue(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", itemRequest.getProductId()));
            BigDecimal unitPrice = itemRequest.getUnitPrice() != null
                    ? itemRequest.getUnitPrice() : product.getSellingPrice();
            BigDecimal itemDiscount = defaultZero(itemRequest.getDiscount());
            BigDecimal lineTotal = unitPrice.multiply(itemRequest.getQuantity()).subtract(itemDiscount);

            SaleItem saleItem = SaleItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(unitPrice)
                    .discount(itemDiscount)
                    .total(lineTotal)
                    .sale(sale)
                    .build();
            saleItem.setActive(true);
            sale.getItems().add(saleItem);
            subtotal = subtotal.add(lineTotal);

            if (sale.getStatus() == SaleStatus.COMPLETED) {
                deductInventory(branch.getId(), product.getId(), itemRequest.getQuantity());
            }
        }

        sale.setSubtotal(subtotal);
        sale.setTotal(subtotal.add(sale.getTax()).subtract(sale.getDiscount()));
        return saleMapper.toResponse(saleRepository.save(sale));
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponse getById(Long id) {
        return saleMapper.toResponse(findActiveById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SaleResponse> getAll(PageRequestDto pageRequest) {
        SalePageRequest request = new SalePageRequest();
        request.setPage(pageRequest.getPage());
        request.setSize(pageRequest.getSize());
        request.setSortBy(pageRequest.getSortBy());
        request.setSortDirection(pageRequest.getSortDirection());
        return getAll(request);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SaleResponse> getAll(SalePageRequest pageRequest) {
        Page<Sale> page = saleRepository.findAll(
                SaleSpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, saleMapper::toResponse);
    }

    @Override
    public SaleResponse update(Long id, SaleRequest request) {
        Sale sale = findActiveById(id);
        if (sale.getStatus() != SaleStatus.PENDING) {
            throw new BusinessException("Only pending sales can be updated");
        }
        saleValidator.validateUpdate(id, request);
        sale.getItems().clear();

        sale.setSaleDate(request.getSaleDate() != null ? request.getSaleDate() : sale.getSaleDate());
        sale.setTax(defaultZero(request.getTax()));
        sale.setDiscount(defaultZero(request.getDiscount()));
        if (request.getStatus() != null) {
            sale.setStatus(request.getStatus());
        }
        sale.setBranch(findBranch(request.getBranchId()));
        sale.setCustomer(findCustomerOptional(request.getCustomerId()));

        BigDecimal subtotal = BigDecimal.ZERO;
        for (SaleItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findByIdAndActiveTrue(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", itemRequest.getProductId()));
            BigDecimal unitPrice = itemRequest.getUnitPrice() != null
                    ? itemRequest.getUnitPrice() : product.getSellingPrice();
            BigDecimal itemDiscount = defaultZero(itemRequest.getDiscount());
            BigDecimal lineTotal = unitPrice.multiply(itemRequest.getQuantity()).subtract(itemDiscount);

            SaleItem saleItem = SaleItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(unitPrice)
                    .discount(itemDiscount)
                    .total(lineTotal)
                    .sale(sale)
                    .build();
            saleItem.setActive(true);
            sale.getItems().add(saleItem);
            subtotal = subtotal.add(lineTotal);
        }
        sale.setSubtotal(subtotal);
        sale.setTotal(subtotal.add(sale.getTax()).subtract(sale.getDiscount()));
        return saleMapper.toResponse(saleRepository.save(sale));
    }

    @Override
    public void delete(Long id) {
        Sale sale = findActiveById(id);
        sale.setActive(false);
        saleRepository.save(sale);
    }

    private Sale findActiveById(Long id) {
        return saleRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", "id", id));
    }

    private Branch findBranch(Long branchId) {
        return branchRepository.findByIdAndActiveTrue(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", branchId));
    }

    private Customer findCustomerOptional(Long customerId) {
        if (customerId == null) {
            return null;
        }
        return customerRepository.findByIdAndActiveTrue(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
    }

    private User findCashier() {
        Long userId = SecurityUtils.getCurrentUser() != null ? SecurityUtils.getCurrentUser().getId() : null;
        if (userId == null) {
            throw new BusinessException("Authenticated user is required to create a sale");
        }
        return userRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    private void deductInventory(Long branchId, Long productId, BigDecimal quantity) {
        Inventory inventory = inventoryRepository
                .findByBranchIdAndProductIdAndActiveTrue(branchId, productId)
                .orElseThrow(() -> new BusinessException(
                        "Insufficient inventory for product id: " + productId));
        if (inventory.getQuantity().compareTo(quantity) < 0) {
            throw new BusinessException("Insufficient stock for product id: " + productId);
        }
        inventory.setQuantity(inventory.getQuantity().subtract(quantity));
        inventoryRepository.save(inventory);
    }

    private String generateSaleNumber() {
        return "SALE-" + Instant.now().toEpochMilli() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private BigDecimal defaultZero(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}
