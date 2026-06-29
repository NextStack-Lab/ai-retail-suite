package com.nextstack.airetail.sales.dto.request;

import com.nextstack.airetail.sales.entity.SaleStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Request DTO for creating or updating a sale.
 */
@Getter
@Setter
public class SaleRequest {

    private Instant saleDate;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal tax;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal discount;

    private SaleStatus status;

    @NotNull(message = "Branch ID is required")
    private Long branchId;

    private Long customerId;

    @NotEmpty(message = "At least one sale item is required")
    @Valid
    private List<SaleItemRequest> items;
}
