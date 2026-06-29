package com.nextstack.airetail.inventory.service.impl;

import com.nextstack.airetail.branch.entity.Branch;
import com.nextstack.airetail.branch.repository.BranchRepository;
import com.nextstack.airetail.common.dto.PageRequestDto;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.PageResponseMapper;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import com.nextstack.airetail.inventory.dto.request.InventoryPageRequest;
import com.nextstack.airetail.inventory.dto.request.InventoryRequest;
import com.nextstack.airetail.inventory.dto.response.InventoryResponse;
import com.nextstack.airetail.inventory.entity.Inventory;
import com.nextstack.airetail.inventory.mapper.InventoryMapper;
import com.nextstack.airetail.inventory.repository.InventoryRepository;
import com.nextstack.airetail.inventory.service.InventoryService;
import com.nextstack.airetail.inventory.specification.InventorySpecifications;
import com.nextstack.airetail.inventory.validator.InventoryValidator;
import com.nextstack.airetail.product.entity.Product;
import com.nextstack.airetail.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link InventoryService}.
 */
@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final InventoryMapper inventoryMapper;
    private final InventoryValidator inventoryValidator;

    /**
     * Creates the service with required dependencies.
     */
    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                BranchRepository branchRepository,
                                ProductRepository productRepository,
                                InventoryMapper inventoryMapper,
                                InventoryValidator inventoryValidator) {
        this.inventoryRepository = inventoryRepository;
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
        this.inventoryMapper = inventoryMapper;
        this.inventoryValidator = inventoryValidator;
    }

    @Override
    public InventoryResponse create(InventoryRequest request) {
        inventoryValidator.validateCreate(request);
        Inventory inventory = inventoryMapper.toEntity(request);
        inventory.setBranch(findBranch(request.getBranchId()));
        inventory.setProduct(findProduct(request.getProductId()));
        inventory.setActive(true);
        return inventoryMapper.toResponse(inventoryRepository.save(inventory));
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getById(Long id) {
        return inventoryMapper.toResponse(findActiveById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<InventoryResponse> getAll(PageRequestDto pageRequest) {
        InventoryPageRequest request = new InventoryPageRequest();
        request.setPage(pageRequest.getPage());
        request.setSize(pageRequest.getSize());
        request.setSortBy(pageRequest.getSortBy());
        request.setSortDirection(pageRequest.getSortDirection());
        return getAll(request);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<InventoryResponse> getAll(InventoryPageRequest pageRequest) {
        Page<Inventory> page = inventoryRepository.findAll(
                InventorySpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, inventoryMapper::toResponse);
    }

    @Override
    public InventoryResponse update(Long id, InventoryRequest request) {
        Inventory inventory = findActiveById(id);
        inventoryValidator.validateUpdate(id, request);
        inventoryMapper.updateEntity(request, inventory);
        inventory.setBranch(findBranch(request.getBranchId()));
        inventory.setProduct(findProduct(request.getProductId()));
        return inventoryMapper.toResponse(inventoryRepository.save(inventory));
    }

    @Override
    public void delete(Long id) {
        Inventory inventory = findActiveById(id);
        inventory.setActive(false);
        inventoryRepository.save(inventory);
    }

    private Inventory findActiveById(Long id) {
        return inventoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", "id", id));
    }

    private Branch findBranch(Long branchId) {
        return branchRepository.findByIdAndActiveTrue(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", branchId));
    }

    private Product findProduct(Long productId) {
        return productRepository.findByIdAndActiveTrue(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }
}
