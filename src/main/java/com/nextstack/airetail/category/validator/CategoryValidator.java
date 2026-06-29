package com.nextstack.airetail.category.validator;

import com.nextstack.airetail.category.dto.request.CategoryRequest;
import com.nextstack.airetail.category.repository.CategoryRepository;
import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.exception.BusinessException;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Business validation rules for category operations.
 */
@Component
public class CategoryValidator {

    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;

    /**
     * Creates the validator with required dependencies.
     */
    public CategoryValidator(CategoryRepository categoryRepository, CompanyRepository companyRepository) {
        this.categoryRepository = categoryRepository;
        this.companyRepository = companyRepository;
    }

    /**
     * Validates a create request.
     *
     * @param request category request
     */
    public void validateCreate(CategoryRequest request) {
        ensureCompanyExists(request.getCompanyId());
        validateParent(request.getParentId());
    }

    /**
     * Validates an update request.
     *
     * @param id      category identifier
     * @param request category request
     */
    public void validateUpdate(Long id, CategoryRequest request) {
        ensureCompanyExists(request.getCompanyId());
        if (request.getParentId() != null && request.getParentId().equals(id)) {
            throw new BusinessException("Category cannot be its own parent");
        }
        validateParent(request.getParentId());
    }

    private void ensureCompanyExists(Long companyId) {
        companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
    }

    private void validateParent(Long parentId) {
        if (parentId == null) {
            return;
        }
        categoryRepository.findByIdAndActiveTrue(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", parentId));
    }
}
