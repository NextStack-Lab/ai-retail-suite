package com.nextstack.airetail.role.validator;

import com.nextstack.airetail.exception.BusinessException;
import com.nextstack.airetail.role.dto.request.RoleRequest;
import com.nextstack.airetail.role.repository.RoleRepository;
import org.springframework.stereotype.Component;

/**
 * Business validation rules for role operations.
 */
@Component
public class RoleValidator {

    private final RoleRepository roleRepository;

    /**
     * Creates the validator with repository dependency.
     */
    public RoleValidator(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Validates a create request.
     *
     * @param request role request
     */
    public void validateCreate(RoleRequest request) {
        if (request.getCompanyId() != null
                && roleRepository.existsByCodeAndCompanyId(request.getCode(), request.getCompanyId())) {
            throw new BusinessException("Role code already exists for this company: " + request.getCode());
        }
    }

    /**
     * Validates an update request.
     *
     * @param id      role identifier
     * @param request role request
     */
    public void validateUpdate(Long id, RoleRequest request) {
        if (request.getCompanyId() != null
                && roleRepository.existsByCodeAndCompanyIdAndIdNot(
                request.getCode(), request.getCompanyId(), id)) {
            throw new BusinessException("Role code already exists for this company: " + request.getCode());
        }
    }
}
