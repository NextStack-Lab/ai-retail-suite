package com.nextstack.airetail.role.specification;

import com.nextstack.airetail.common.specification.BaseSpecifications;
import com.nextstack.airetail.role.dto.request.RolePageRequest;
import com.nextstack.airetail.role.entity.Role;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for role queries.
 */
public final class RoleSpecifications {

    private RoleSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<Role> fromRequest(RolePageRequest request) {
        return Specification.where(BaseSpecifications.<Role>isActive())
                .and(BaseSpecifications.<Role>containsIgnoreCase("name", request.getSearch()))
                .and(companyIdEquals(request.getCompanyId()));
    }

    private static Specification<Role> companyIdEquals(Long companyId) {
        return (root, query, cb) -> {
            if (companyId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("company").get("id"), companyId);
        };
    }
}
