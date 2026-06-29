package com.nextstack.airetail.company.validator;

import com.nextstack.airetail.company.dto.request.CompanyRequest;
import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.exception.BusinessException;
import org.springframework.stereotype.Component;

/**
 * Business validation rules for company operations.
 */
@Component
public class CompanyValidator {

    private final CompanyRepository companyRepository;

    /**
     * Creates the validator with repository dependency.
     *
     * @param companyRepository company data access
     */
    public CompanyValidator(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Validates a create request.
     *
     * @param request company request
     */
    public void validateCreate(CompanyRequest request) {
        if (companyRepository.existsByCode(request.getCode())) {
            throw new BusinessException("Company code already exists: " + request.getCode());
        }
    }

    /**
     * Validates an update request.
     *
     * @param id      company identifier
     * @param request company request
     */
    public void validateUpdate(Long id, CompanyRequest request) {
        if (companyRepository.existsByCodeAndIdNot(request.getCode(), id)) {
            throw new BusinessException("Company code already exists: " + request.getCode());
        }
    }
}
