package com.nextstack.airetail.audit.controller;

import com.nextstack.airetail.audit.dto.request.AuditLogPageRequest;
import com.nextstack.airetail.audit.dto.response.AuditLogResponse;
import com.nextstack.airetail.audit.service.AuditService;
import com.nextstack.airetail.common.dto.ApiResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for read-only audit log access.
 */
@RestController
@RequestMapping("/v1/audit-logs")
public class AuditController {

    private final AuditService auditService;

    /**
     * Creates the controller with audit service dependency.
     */
    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * Returns paginated audit logs with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @param httpRequest HTTP request for path logging
     * @return paginated audit logs
     */
    @GetMapping
    @PreAuthorize("hasAuthority('REPORT_READ')")
    public ApiResponse<PageResponse<AuditLogResponse>> getAll(AuditLogPageRequest pageRequest,
                                                                HttpServletRequest httpRequest) {
        return ApiResponse.success("Audit logs retrieved successfully",
                auditService.getAll(pageRequest), httpRequest.getRequestURI());
    }
}
