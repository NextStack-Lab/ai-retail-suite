package com.nextstack.airetail.role.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Request DTO for creating or updating a role.
 */
@Getter
@Setter
public class RoleRequest {

    @NotBlank(message = "Role name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Role code is required")
    @Size(max = 100)
    private String code;

    @Size(max = 500)
    private String description;

    private Long companyId;

    private Set<Long> permissionIds;
}
