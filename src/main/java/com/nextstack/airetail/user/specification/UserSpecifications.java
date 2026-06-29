package com.nextstack.airetail.user.specification;

import com.nextstack.airetail.common.specification.BaseSpecifications;
import com.nextstack.airetail.user.dto.request.UserPageRequest;
import com.nextstack.airetail.user.entity.User;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for user queries.
 */
public final class UserSpecifications {

    private UserSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<User> fromRequest(UserPageRequest request) {
        return Specification.where(BaseSpecifications.<User>isActive())
                .and(searchFilter(request.getSearch()))
                .and(equalsLong("company", "id", request.getCompanyId()))
                .and(equalsLong("branch", "id", request.getBranchId()));
    }

    private static Specification<User> searchFilter(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return cb.conjunction();
            }
            String pattern = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("username")), pattern),
                    cb.like(cb.lower(root.get("email")), pattern),
                    cb.like(cb.lower(root.get("firstName")), pattern),
                    cb.like(cb.lower(root.get("lastName")), pattern));
        };
    }

    private static Specification<User> equalsLong(String relation, String field, Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(relation).get(field), value);
        };
    }
}
