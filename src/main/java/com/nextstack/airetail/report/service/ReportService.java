package com.nextstack.airetail.report.service;

import com.nextstack.airetail.report.dto.response.InventoryReportResponse;
import com.nextstack.airetail.report.dto.response.SalesReportResponse;

import java.time.Instant;

/**
 * Service contract for report generation.
 */
public interface ReportService {

    /**
     * Generates a sales report for the given date range and optional branch.
     *
     * @param startDate report start date
     * @param endDate   report end date
     * @param branchId  optional branch filter
     * @return sales report
     */
    SalesReportResponse getSalesReport(Instant startDate, Instant endDate, Long branchId);

    /**
     * Generates an inventory report for the given branch.
     *
     * @param branchId     optional branch filter
     * @param lowStockOnly whether to include only low-stock items
     * @return inventory report
     */
    InventoryReportResponse getInventoryReport(Long branchId, boolean lowStockOnly);
}
