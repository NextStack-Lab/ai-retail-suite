package com.nextstack.airetail.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Response DTO for category data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private Long companyId;
    private String companyName;
    private Long parentId;
    private String parentName;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
