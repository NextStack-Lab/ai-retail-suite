package com.nextstack.airetail.sales.specification;

import com.nextstack.airetail.common.specification.BaseSpecifications;
import com.nextstack.airetail.sales.dto.request.SalePageRequest;
import com.nextstack.airetail.sales.entity.Sale;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for sale queries.
 */
public final class SaleSpecifications {

    private SaleSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<Sale> fromRequest(SalePageRequest request) {
        return Specification.where(BaseSpecifications.<Sale>isActive())
                .and(BaseSpecifications.<Sale>containsIgnoreCase("saleNumber", request.getSearch()))
                .and(branchIdEquals(request.getBranchId()))
                .and(customerIdEquals(request.getCustomerId()))
                .and(statusEquals(request.getStatus()))
                .and(dateRange(request.getStartDate(), request.getEndDate()));
    }

    private static Specification<Sale> branchIdEquals(Long branchId) {
        return (root, query, cb) -> {
            if (branchId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("branch").get("id"), branchId);
        };
    }

    private static Specification<Sale> customerIdEquals(Long customerId) {
        return (root, query, cb) -> {
            if (customerId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("customer").get("id"), customerId);
        };
    }

    private static Specification<Sale> statusEquals(com.nextstack.airetail.sales.entity.SaleStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("status"), status);
        };
    }

    private static Specification<Sale> dateRange(java.time.Instant startDate, java.time.Instant endDate) {
        return (root, query, cb) -> {
            if (startDate == null && endDate == null) {
                return cb.conjunction();
            }
            if (startDate != null && endDate != null) {
                return cb.between(root.get("saleDate"), startDate, endDate);
            }
            if (startDate != null) {
                return cb.greaterThanOrEqualTo(root.get("saleDate"), startDate);
            }
            return cb.lessThanOrEqualTo(root.get("saleDate"), endDate);
        };
    }
}
