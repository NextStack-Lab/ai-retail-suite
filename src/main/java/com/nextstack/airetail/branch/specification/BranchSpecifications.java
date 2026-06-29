package com.nextstack.airetail.branch.specification;

import com.nextstack.airetail.branch.dto.request.BranchPageRequest;
import com.nextstack.airetail.branch.entity.Branch;
import com.nextstack.airetail.common.specification.BaseSpecifications;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for branch queries.
 */
public final class BranchSpecifications {

    private BranchSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<Branch> fromRequest(BranchPageRequest request) {
        return Specification.where(BaseSpecifications.<Branch>isActive())
                .and(BaseSpecifications.<Branch>containsIgnoreCase("name", request.getSearch()))
                .and(companyIdEquals(request.getCompanyId()));
    }

    private static Specification<Branch> companyIdEquals(Long companyId) {
        return (root, query, cb) -> {
            if (companyId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("company").get("id"), companyId);
        };
    }
}
