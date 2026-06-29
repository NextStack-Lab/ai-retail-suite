package com.nextstack.airetail.product.validator;

import com.nextstack.airetail.brand.repository.BrandRepository;
import com.nextstack.airetail.category.repository.CategoryRepository;
import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.exception.BusinessException;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import com.nextstack.airetail.product.dto.request.ProductRequest;
import com.nextstack.airetail.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

/**
 * Business validation rules for product operations.
 */
@Component
public class ProductValidator {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    /**
     * Creates the validator with required dependencies.
     */
    public ProductValidator(ProductRepository productRepository,
                            CompanyRepository companyRepository,
                            CategoryRepository categoryRepository,
                            BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    /**
     * Validates a create request.
     *
     * @param request product request
     */
    public void validateCreate(ProductRequest request) {
        ensureCompanyExists(request.getCompanyId());
        validateReferences(request);
        if (productRepository.existsBySkuAndCompanyId(request.getSku(), request.getCompanyId())) {
            throw new BusinessException("Product SKU already exists for this company: " + request.getSku());
        }
    }

    /**
     * Validates an update request.
     *
     * @param id      product identifier
     * @param request product request
     */
    public void validateUpdate(Long id, ProductRequest request) {
        ensureCompanyExists(request.getCompanyId());
        validateReferences(request);
        if (productRepository.existsBySkuAndCompanyIdAndIdNot(
                request.getSku(), request.getCompanyId(), id)) {
            throw new BusinessException("Product SKU already exists for this company: " + request.getSku());
        }
    }

    private void ensureCompanyExists(Long companyId) {
        companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
    }

    private void validateReferences(ProductRequest request) {
        if (request.getCategoryId() != null) {
            categoryRepository.findByIdAndActiveTrue(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));
        }
        if (request.getBrandId() != null) {
            brandRepository.findByIdAndActiveTrue(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", request.getBrandId()));
        }
    }
}
