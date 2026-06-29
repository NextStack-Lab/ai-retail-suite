package com.nextstack.airetail.inventory.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Request DTO for creating or updating inventory records.
 */
@Getter
@Setter
public class InventoryRequest {

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal quantity;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal reorderLevel;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal reorderQuantity;

    @NotNull(message = "Branch ID is required")
    private Long branchId;

    @NotNull(message = "Product ID is required")
    private Long productId;
}
