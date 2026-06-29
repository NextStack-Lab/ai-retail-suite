package com.nextstack.airetail.audit.service;

import com.nextstack.airetail.audit.dto.request.AuditLogPageRequest;
import com.nextstack.airetail.audit.dto.response.AuditLogResponse;
import com.nextstack.airetail.common.dto.PageResponse;

/**
 * Service contract for audit logging and retrieval.
 */
public interface AuditService {

    /**
     * Logs a system action.
     *
     * @param action   action performed
     * @param entity   entity type
     * @param entityId entity identifier
     * @param details  additional details
     * @param ipAddress client IP address
     */
    void logAction(String action, String entity, Long entityId, String details, String ipAddress);

    /**
     * Returns paginated audit logs with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated audit log responses
     */
    PageResponse<AuditLogResponse> getAll(AuditLogPageRequest pageRequest);
}
