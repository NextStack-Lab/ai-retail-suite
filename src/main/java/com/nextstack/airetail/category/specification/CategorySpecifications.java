package com.nextstack.airetail.category.specification;

import com.nextstack.airetail.category.dto.request.CategoryPageRequest;
import com.nextstack.airetail.category.entity.Category;
import com.nextstack.airetail.common.specification.BaseSpecifications;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for category queries.
 */
public final class CategorySpecifications {

    private CategorySpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<Category> fromRequest(CategoryPageRequest request) {
        return Specification.where(BaseSpecifications.<Category>isActive())
                .and(BaseSpecifications.<Category>containsIgnoreCase("name", request.getSearch()))
                .and(companyIdEquals(request.getCompanyId()))
                .and(parentIdEquals(request.getParentId()));
    }

    private static Specification<Category> companyIdEquals(Long companyId) {
        return (root, query, cb) -> companyId == null ? cb.conjunction()
                : cb.equal(root.get("company").get("id"), companyId);
    }

    private static Specification<Category> parentIdEquals(Long parentId) {
        return (root, query, cb) -> parentId == null ? cb.conjunction()
                : cb.equal(root.get("parent").get("id"), parentId);
    }
}
