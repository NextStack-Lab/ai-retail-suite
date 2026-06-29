package com.nextstack.airetail.branch.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Response DTO for branch data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponse {

    private Long id;
    private String name;
    private String code;
    private String address;
    private String phone;
    private String email;
    private Boolean isMain;
    private Long companyId;
    private String companyName;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
