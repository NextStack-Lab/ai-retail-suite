package com.nextstack.airetail.common.specification;

import com.nextstack.airetail.common.entity.BaseEntity;
import org.springframework.data.jpa.domain.Specification;

/**
 * Reusable JPA specifications for common query patterns.
 */
public final class BaseSpecifications {

    private BaseSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Filters only active (non soft-deleted) records.
     *
     * @param <T> entity type extending {@link BaseEntity}
     * @return active-only specification
     */
    public static <T extends BaseEntity> Specification<T> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

    /**
     * Filters by a case-insensitive partial match on a string field.
     *
     * @param field field name
     * @param value search value
     * @param <T>   entity type
     * @return like specification
     */
    public static <T> Specification<T> containsIgnoreCase(String field, String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
        };
    }
}
