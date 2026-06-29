package com.nextstack.airetail.permission.mapper;

import com.nextstack.airetail.permission.dto.response.PermissionResponse;
import com.nextstack.airetail.permission.entity.Permission;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for {@link Permission} entity and DTOs.
 */
@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface PermissionMapper {

    PermissionResponse toResponse(Permission entity);
}
