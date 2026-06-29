package com.nextstack.airetail.sales.dto.response;

import com.nextstack.airetail.sales.entity.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Response DTO for sale data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {

    private Long id;
    private String saleNumber;
    private Instant saleDate;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal total;
    private SaleStatus status;
    private Long branchId;
    private String branchName;
    private Long customerId;
    private String customerName;
    private Long cashierId;
    private String cashierUsername;
    private List<SaleItemResponse> items;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
