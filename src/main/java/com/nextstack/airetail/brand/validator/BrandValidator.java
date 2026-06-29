package com.nextstack.airetail.brand.validator;

import com.nextstack.airetail.brand.dto.request.BrandRequest;
import com.nextstack.airetail.brand.repository.BrandRepository;
import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.exception.BusinessException;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Business validation rules for brand operations.
 */
@Component
public class BrandValidator {

    private final BrandRepository brandRepository;
    private final CompanyRepository companyRepository;

    /**
     * Creates the validator with required dependencies.
     */
    public BrandValidator(BrandRepository brandRepository, CompanyRepository companyRepository) {
        this.brandRepository = brandRepository;
        this.companyRepository = companyRepository;
    }

    /**
     * Validates a create request.
     *
     * @param request brand request
     */
    public void validateCreate(BrandRequest request) {
        ensureCompanyExists(request.getCompanyId());
        if (brandRepository.existsByNameAndCompanyId(request.getName(), request.getCompanyId())) {
            throw new BusinessException("Brand name already exists for this company: " + request.getName());
        }
    }

    /**
     * Validates an update request.
     *
     * @param id      brand identifier
     * @param request brand request
     */
    public void validateUpdate(Long id, BrandRequest request) {
        ensureCompanyExists(request.getCompanyId());
        if (brandRepository.existsByNameAndCompanyIdAndIdNot(
                request.getName(), request.getCompanyId(), id)) {
            throw new BusinessException("Brand name already exists for this company: " + request.getName());
        }
    }

    private void ensureCompanyExists(Long companyId) {
        companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
    }
}
