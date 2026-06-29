package com.nextstack.airetail.product.service.impl;

import com.nextstack.airetail.brand.entity.Brand;
import com.nextstack.airetail.brand.repository.BrandRepository;
import com.nextstack.airetail.category.entity.Category;
import com.nextstack.airetail.category.repository.CategoryRepository;
import com.nextstack.airetail.common.dto.PageRequestDto;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.PageResponseMapper;
import com.nextstack.airetail.company.entity.Company;
import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import com.nextstack.airetail.product.dto.request.ProductPageRequest;
import com.nextstack.airetail.product.dto.request.ProductRequest;
import com.nextstack.airetail.product.dto.response.ProductResponse;
import com.nextstack.airetail.product.entity.Product;
import com.nextstack.airetail.product.mapper.ProductMapper;
import com.nextstack.airetail.product.repository.ProductRepository;
import com.nextstack.airetail.product.service.ProductService;
import com.nextstack.airetail.product.specification.ProductSpecifications;
import com.nextstack.airetail.product.validator.ProductValidator;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link ProductService}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;

    /**
     * Creates the service with required dependencies.
     */
    public ProductServiceImpl(ProductRepository productRepository,
                              CompanyRepository companyRepository,
                              CategoryRepository categoryRepository,
                              BrandRepository brandRepository,
                              ProductMapper productMapper,
                              ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productMapper = productMapper;
        this.productValidator = productValidator;
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        productValidator.validateCreate(request);
        Product product = productMapper.toEntity(request);
        product.setCompany(findCompany(request.getCompanyId()));
        product.setCategory(findCategoryOptional(request.getCategoryId()));
        product.setBrand(findBrandOptional(request.getBrandId()));
        product.setActive(true);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        return productMapper.toResponse(findActiveById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getAll(PageRequestDto pageRequest) {
        ProductPageRequest request = new ProductPageRequest();
        request.setPage(pageRequest.getPage());
        request.setSize(pageRequest.getSize());
        request.setSortBy(pageRequest.getSortBy());
        request.setSortDirection(pageRequest.getSortDirection());
        return getAll(request);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getAll(ProductPageRequest pageRequest) {
        Page<Product> page = productRepository.findAll(
                ProductSpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, productMapper::toResponse);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = findActiveById(id);
        productValidator.validateUpdate(id, request);
        productMapper.updateEntity(request, product);
        product.setCompany(findCompany(request.getCompanyId()));
        product.setCategory(findCategoryOptional(request.getCategoryId()));
        product.setBrand(findBrandOptional(request.getBrandId()));
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public void delete(Long id) {
        Product product = findActiveById(id);
        product.setActive(false);
        productRepository.save(product);
    }

    private Product findActiveById(Long id) {
        return productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    private Company findCompany(Long companyId) {
        return companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
    }

    private Category findCategoryOptional(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return categoryRepository.findByIdAndActiveTrue(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
    }

    private Brand findBrandOptional(Long brandId) {
        if (brandId == null) {
            return null;
        }
        return brandRepository.findByIdAndActiveTrue(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", brandId));
    }
}
