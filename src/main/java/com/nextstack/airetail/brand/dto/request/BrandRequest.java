package com.nextstack.airetail.brand.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating a brand.
 */
@Getter
@Setter
public class BrandRequest {

    @NotBlank(message = "Brand name is required")
    @Size(max = 200)
    private String name;

    @Size(max = 500)
    private String description;

    @Size(max = 500)
    private String logoUrl;

    @NotNull(message = "Company ID is required")
    private Long companyId;
}
