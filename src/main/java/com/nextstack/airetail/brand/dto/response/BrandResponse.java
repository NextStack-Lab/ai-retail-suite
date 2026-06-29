package com.nextstack.airetail.brand.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Response DTO for brand data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {

    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private Long companyId;
    private String companyName;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
