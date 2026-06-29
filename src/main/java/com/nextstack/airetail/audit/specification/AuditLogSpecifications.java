package com.nextstack.airetail.audit.specification;

import com.nextstack.airetail.audit.dto.request.AuditLogPageRequest;
import com.nextstack.airetail.audit.entity.AuditLog;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for audit log queries.
 */
public final class AuditLogSpecifications {

    private AuditLogSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<AuditLog> fromRequest(AuditLogPageRequest request) {
        return Specification.where(actionEquals(request.getAction()))
                .and(entityEquals(request.getEntity()))
                .and(entityIdEquals(request.getEntityId()))
                .and(userIdEquals(request.getUserId()));
    }

    private static Specification<AuditLog> actionEquals(String action) {
        return (root, query, cb) -> {
            if (action == null || action.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("action"), action);
        };
    }

    private static Specification<AuditLog> entityEquals(String entity) {
        return (root, query, cb) -> {
            if (entity == null || entity.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("entity"), entity);
        };
    }

    private static Specification<AuditLog> entityIdEquals(Long entityId) {
        return (root, query, cb) -> {
            if (entityId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("entityId"), entityId);
        };
    }

    private static Specification<AuditLog> userIdEquals(Long userId) {
        return (root, query, cb) -> {
            if (userId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("userId"), userId);
        };
    }
}
