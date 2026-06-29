package com.nextstack.airetail.company.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Response DTO for company data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {

    private Long id;
    private String name;
    private String code;
    private String email;
    private String phone;
    private String address;
    private String taxId;
    private String logoUrl;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
