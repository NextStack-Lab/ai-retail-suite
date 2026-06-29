package com.nextstack.airetail.audit.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Paginated audit log list request with optional search filters.
 */
@Getter
@Setter
public class AuditLogPageRequest extends PageRequestDto {

    private String action;
    private String entity;
    private Long entityId;
    private Long userId;
}
