package com.nextstack.airetail.role.mapper;

import com.nextstack.airetail.permission.entity.Permission;
import com.nextstack.airetail.role.dto.request.RoleRequest;
import com.nextstack.airetail.role.dto.response.RoleResponse;
import com.nextstack.airetail.role.entity.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * MapStruct mapper for {@link Role} entity and DTOs.
 */
@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "active", ignore = true)
    Role toEntity(RoleRequest request);

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(target = "permissionCodes", expression = "java(mapPermissionCodes(entity.getPermissions()))")
    RoleResponse toResponse(Role entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    void updateEntity(RoleRequest request, @MappingTarget Role entity);

    default Set<String> mapPermissionCodes(Set<Permission> permissions) {
        if (permissions == null) {
            return Set.of();
        }
        return permissions.stream().map(Permission::getCode).collect(Collectors.toSet());
    }
}
