package com.nextstack.airetail.role.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

/**
 * Response DTO for role data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

    private Long id;
    private String name;
    private String code;
    private String description;
    private Long companyId;
    private String companyName;
    private Set<String> permissionCodes;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
