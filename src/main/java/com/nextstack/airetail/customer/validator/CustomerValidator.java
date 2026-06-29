package com.nextstack.airetail.customer.validator;

import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.customer.dto.request.CustomerRequest;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Business validation rules for customer operations.
 */
@Component
public class CustomerValidator {

    private final CompanyRepository companyRepository;

    /**
     * Creates the validator with required dependencies.
     */
    public CustomerValidator(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Validates a create request.
     *
     * @param request customer request
     */
    public void validateCreate(CustomerRequest request) {
        ensureCompanyExists(request.getCompanyId());
    }

    /**
     * Validates an update request.
     *
     * @param id      customer identifier
     * @param request customer request
     */
    public void validateUpdate(Long id, CustomerRequest request) {
        ensureCompanyExists(request.getCompanyId());
    }

    private void ensureCompanyExists(Long companyId) {
        companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
    }
}
