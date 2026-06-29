package com.nextstack.airetail.report.controller;

import com.nextstack.airetail.common.dto.ApiResponse;
import com.nextstack.airetail.report.dto.response.InventoryReportResponse;
import com.nextstack.airetail.report.dto.response.SalesReportResponse;
import com.nextstack.airetail.report.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * REST controller for report endpoints.
 */
@RestController
@RequestMapping("/v1/reports")
public class ReportController {

    private final ReportService reportService;

    /**
     * Creates the controller with report service dependency.
     */
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Returns a sales report for the given date range.
     *
     * @param startDate   report start date
     * @param endDate     report end date
     * @param branchId    optional branch filter
     * @param httpRequest HTTP request for path logging
     * @return sales report
     */
    @GetMapping("/sales")
    @PreAuthorize("hasAuthority('REPORT_READ')")
    public ApiResponse<SalesReportResponse> getSalesReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate,
            @RequestParam(required = false) Long branchId,
            HttpServletRequest httpRequest) {
        return ApiResponse.success("Sales report generated successfully",
                reportService.getSalesReport(startDate, endDate, branchId), httpRequest.getRequestURI());
    }

    /**
     * Returns an inventory report.
     *
     * @param branchId     optional branch filter
     * @param lowStockOnly whether to include only low-stock items
     * @param httpRequest  HTTP request for path logging
     * @return inventory report
     */
    @GetMapping("/inventory")
    @PreAuthorize("hasAuthority('REPORT_READ')")
    public ApiResponse<InventoryReportResponse> getInventoryReport(
            @RequestParam(required = false) Long branchId,
            @RequestParam(defaultValue = "false") boolean lowStockOnly,
            HttpServletRequest httpRequest) {
        return ApiResponse.success("Inventory report generated successfully",
                reportService.getInventoryReport(branchId, lowStockOnly), httpRequest.getRequestURI());
    }
}
