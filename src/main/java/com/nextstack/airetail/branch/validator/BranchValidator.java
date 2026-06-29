package com.nextstack.airetail.branch.validator;

import com.nextstack.airetail.branch.dto.request.BranchRequest;
import com.nextstack.airetail.branch.repository.BranchRepository;
import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.exception.BusinessException;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Business validation rules for branch operations.
 */
@Component
public class BranchValidator {

    private final BranchRepository branchRepository;
    private final CompanyRepository companyRepository;

    /**
     * Creates the validator with required dependencies.
     */
    public BranchValidator(BranchRepository branchRepository, CompanyRepository companyRepository) {
        this.branchRepository = branchRepository;
        this.companyRepository = companyRepository;
    }

    /**
     * Validates a create request.
     *
     * @param request branch request
     */
    public void validateCreate(BranchRequest request) {
        ensureCompanyExists(request.getCompanyId());
        if (branchRepository.existsByCodeAndCompanyId(request.getCode(), request.getCompanyId())) {
            throw new BusinessException("Branch code already exists for this company: " + request.getCode());
        }
    }

    /**
     * Validates an update request.
     *
     * @param id      branch identifier
     * @param request branch request
     */
    public void validateUpdate(Long id, BranchRequest request) {
        ensureCompanyExists(request.getCompanyId());
        if (branchRepository.existsByCodeAndCompanyIdAndIdNot(
                request.getCode(), request.getCompanyId(), id)) {
            throw new BusinessException("Branch code already exists for this company: " + request.getCode());
        }
    }

    private void ensureCompanyExists(Long companyId) {
        companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
    }
}
