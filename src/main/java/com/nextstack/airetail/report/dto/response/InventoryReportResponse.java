package com.nextstack.airetail.report.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for inventory report data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReportResponse {

    private Long branchId;
    private long totalProducts;
    private long lowStockCount;
    private BigDecimal totalQuantity;
    private List<InventoryReportItem> items;

    /**
     * Individual inventory report line item.
     */
    @Getter
@Setter
    @Builder
    public static class InventoryReportItem {
        private Long productId;
        private String productSku;
        private String productName;
        private BigDecimal quantity;
        private BigDecimal reorderLevel;
        private boolean lowStock;
    }
}
