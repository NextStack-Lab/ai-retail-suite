package com.nextstack.airetail.permission.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Response DTO for permission data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse {

    private Long id;
    private String name;
    private String code;
    private String resource;
    private String action;
    private String description;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
