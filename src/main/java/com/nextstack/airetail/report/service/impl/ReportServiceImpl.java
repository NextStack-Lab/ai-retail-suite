package com.nextstack.airetail.report.service.impl;

import com.nextstack.airetail.inventory.entity.Inventory;
import com.nextstack.airetail.inventory.repository.InventoryRepository;
import com.nextstack.airetail.inventory.specification.InventorySpecifications;
import com.nextstack.airetail.inventory.dto.request.InventoryPageRequest;
import com.nextstack.airetail.report.dto.response.InventoryReportResponse;
import com.nextstack.airetail.report.dto.response.SalesReportResponse;
import com.nextstack.airetail.report.service.ReportService;
import com.nextstack.airetail.sales.entity.SaleStatus;
import com.nextstack.airetail.sales.repository.SaleRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ReportService}.
 */
@Service
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final SaleRepository saleRepository;
    private final InventoryRepository inventoryRepository;

    /**
     * Creates the service with required dependencies.
     */
    public ReportServiceImpl(SaleRepository saleRepository, InventoryRepository inventoryRepository) {
        this.saleRepository = saleRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public SalesReportResponse getSalesReport(Instant startDate, Instant endDate, Long branchId) {
        Instant start = startDate != null ? startDate : Instant.EPOCH;
        Instant end = endDate != null ? endDate : Instant.now();

        BigDecimal totalRevenue = saleRepository.sumTotalByDateRangeAndBranch(
                start, end, branchId, SaleStatus.COMPLETED);
        long totalSales = saleRepository.countByDateRangeAndBranch(
                start, end, branchId, SaleStatus.COMPLETED);

        BigDecimal average = totalSales > 0
                ? totalRevenue.divide(BigDecimal.valueOf(totalSales), 4, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return SalesReportResponse.builder()
                .startDate(start)
                .endDate(end)
                .branchId(branchId)
                .totalSales(totalSales)
                .totalRevenue(totalRevenue)
                .averageSaleValue(average)
                .build();
    }

    @Override
    public InventoryReportResponse getInventoryReport(Long branchId, boolean lowStockOnly) {
        InventoryPageRequest pageRequest = new InventoryPageRequest();
        pageRequest.setBranchId(branchId);
        pageRequest.setLowStockOnly(lowStockOnly);
        pageRequest.setPage(0);
        pageRequest.setSize(Integer.MAX_VALUE);

        Specification<Inventory> spec = InventorySpecifications.fromRequest(pageRequest);
        List<Inventory> inventories = inventoryRepository.findAll(spec);

        List<InventoryReportResponse.InventoryReportItem> items = inventories.stream()
                .map(inv -> InventoryReportResponse.InventoryReportItem.builder()
                        .productId(inv.getProduct().getId())
                        .productSku(inv.getProduct().getSku())
                        .productName(inv.getProduct().getName())
                        .quantity(inv.getQuantity())
                        .reorderLevel(inv.getReorderLevel())
                        .lowStock(isLowStock(inv))
                        .build())
                .collect(Collectors.toList());

        long lowStockCount = items.stream().filter(InventoryReportResponse.InventoryReportItem::isLowStock).count();
        BigDecimal totalQuantity = items.stream()
                .map(InventoryReportResponse.InventoryReportItem::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return InventoryReportResponse.builder()
                .branchId(branchId)
                .totalProducts(items.size())
                .lowStockCount(lowStockCount)
                .totalQuantity(totalQuantity)
                .items(items)
                .build();
    }

    private boolean isLowStock(Inventory inventory) {
        if (inventory.getReorderLevel() == null) {
            return false;
        }
        return inventory.getQuantity().compareTo(inventory.getReorderLevel()) <= 0;
    }
}
