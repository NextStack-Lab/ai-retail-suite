package com.nextstack.airetail.company.specification;

import com.nextstack.airetail.common.specification.BaseSpecifications;
import com.nextstack.airetail.company.dto.request.CompanyPageRequest;
import com.nextstack.airetail.company.entity.Company;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for company queries.
 */
public final class CompanySpecifications {

    private CompanySpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<Company> fromRequest(CompanyPageRequest request) {
        return Specification.where(BaseSpecifications.<Company>isActive())
                .and(BaseSpecifications.<Company>containsIgnoreCase("name", request.getSearch()))
                .and(BaseSpecifications.<Company>containsIgnoreCase("code", request.getCode()));
    }
}
