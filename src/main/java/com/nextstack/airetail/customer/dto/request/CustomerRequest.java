package com.nextstack.airetail.customer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating a customer.
 */
@Getter
@Setter
public class CustomerRequest {

    @NotBlank(message = "Customer name is required")
    @Size(max = 200)
    private String name;

    @Email(message = "Invalid email format")
    @Size(max = 200)
    private String email;

    @Size(max = 20)
    private String phone;

    @Size(max = 500)
    private String address;

    private Integer loyaltyPoints;

    @NotNull(message = "Company ID is required")
    private Long companyId;
}
