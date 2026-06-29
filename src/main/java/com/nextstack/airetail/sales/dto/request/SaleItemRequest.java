package com.nextstack.airetail.sales.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Request DTO for a sale line item.
 */
@Getter
@Setter
public class SaleItemRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal quantity;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal unitPrice;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal discount;
}
