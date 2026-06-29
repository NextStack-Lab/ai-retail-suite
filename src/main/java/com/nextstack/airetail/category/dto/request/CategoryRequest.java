package com.nextstack.airetail.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for creating or updating a category.
 */
@Getter
@Setter
public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(max = 200)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    private Long parentId;
}
