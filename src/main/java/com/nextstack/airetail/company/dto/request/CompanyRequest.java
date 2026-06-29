package com.nextstack.airetail.company.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating a company.
 */
@Getter
@Setter
public class CompanyRequest {

    @NotBlank(message = "Company name is required")
    @Size(max = 200, message = "Company name must not exceed 200 characters")
    private String name;

    @NotBlank(message = "Company code is required")
    @Size(max = 50, message = "Company code must not exceed 50 characters")
    private String code;

    @Email(message = "Invalid email format")
    @Size(max = 200)
    private String email;

    @Size(max = 20)
    private String phone;

    @Size(max = 500)
    private String address;

    @Size(max = 50)
    private String taxId;

    @Size(max = 500)
    private String logoUrl;
}
