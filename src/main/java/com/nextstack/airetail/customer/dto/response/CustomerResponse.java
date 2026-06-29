package com.nextstack.airetail.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Response DTO for customer data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Integer loyaltyPoints;
    private Long companyId;
    private String companyName;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
