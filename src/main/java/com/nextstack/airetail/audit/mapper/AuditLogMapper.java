package com.nextstack.airetail.audit.mapper;

import com.nextstack.airetail.audit.dto.response.AuditLogResponse;
import com.nextstack.airetail.audit.entity.AuditLog;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for {@link AuditLog} entity and DTOs.
 */
@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface AuditLogMapper {

    AuditLogResponse toResponse(AuditLog entity);
}
