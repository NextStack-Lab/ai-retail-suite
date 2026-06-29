package com.nextstack.airetail.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

/**
 * Response DTO for user data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Instant lastLoginAt;
    private Long companyId;
    private String companyName;
    private Long branchId;
    private String branchName;
    private Set<String> roleCodes;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
