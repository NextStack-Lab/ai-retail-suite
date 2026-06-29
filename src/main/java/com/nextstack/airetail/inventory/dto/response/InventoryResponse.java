package com.nextstack.airetail.inventory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Response DTO for inventory data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    private Long id;
    private BigDecimal quantity;
    private BigDecimal reorderLevel;
    private BigDecimal reorderQuantity;
    private Long branchId;
    private String branchName;
    private Long productId;
    private String productSku;
    private String productName;
    private Boolean lowStock;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
