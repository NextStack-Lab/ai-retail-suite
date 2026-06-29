package com.nextstack.airetail.permission.specification;

import com.nextstack.airetail.common.specification.BaseSpecifications;
import com.nextstack.airetail.permission.dto.request.PermissionPageRequest;
import com.nextstack.airetail.permission.entity.Permission;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for permission queries.
 */
public final class PermissionSpecifications {

    private PermissionSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<Permission> fromRequest(PermissionPageRequest request) {
        return Specification.where(BaseSpecifications.<Permission>isActive())
                .and(BaseSpecifications.<Permission>containsIgnoreCase("name", request.getSearch()))
                .and(BaseSpecifications.<Permission>containsIgnoreCase("resource", request.getResource()));
    }
}
