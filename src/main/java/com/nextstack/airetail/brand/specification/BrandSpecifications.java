package com.nextstack.airetail.brand.specification;

import com.nextstack.airetail.brand.dto.request.BrandPageRequest;
import com.nextstack.airetail.brand.entity.Brand;
import com.nextstack.airetail.common.specification.BaseSpecifications;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for brand queries.
 */
public final class BrandSpecifications {

    private BrandSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<Brand> fromRequest(BrandPageRequest request) {
        return Specification.where(BaseSpecifications.<Brand>isActive())
                .and(BaseSpecifications.<Brand>containsIgnoreCase("name", request.getSearch()))
                .and(companyIdEquals(request.getCompanyId()));
    }

    private static Specification<Brand> companyIdEquals(Long companyId) {
        return (root, query, cb) -> {
            if (companyId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("company").get("id"), companyId);
        };
    }
}
