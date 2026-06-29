package com.nextstack.airetail.branch.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating a branch.
 */
@Getter
@Setter
public class BranchRequest {

    @NotBlank(message = "Branch name is required")
    @Size(max = 200)
    private String name;

    @NotBlank(message = "Branch code is required")
    @Size(max = 50)
    private String code;

    @Size(max = 500)
    private String address;

    @Size(max = 20)
    private String phone;

    @Email(message = "Invalid email format")
    @Size(max = 200)
    private String email;

    private Boolean isMain = false;

    @NotNull(message = "Company ID is required")
    private Long companyId;
}
