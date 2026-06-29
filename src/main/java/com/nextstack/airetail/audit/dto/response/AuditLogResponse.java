package com.nextstack.airetail.audit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Response DTO for audit log data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {

    private Long id;
    private String action;
    private String entity;
    private Long entityId;
    private Long userId;
    private String details;
    private String ipAddress;
    private Instant createdAt;
}
