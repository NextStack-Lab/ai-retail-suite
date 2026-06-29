package com.nextstack.airetail.report.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Response DTO for sales report data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesReportResponse {

    private Instant startDate;
    private Instant endDate;
    private Long branchId;
    private long totalSales;
    private BigDecimal totalRevenue;
    private BigDecimal averageSaleValue;
}
