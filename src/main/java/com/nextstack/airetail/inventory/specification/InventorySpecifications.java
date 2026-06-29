package com.nextstack.airetail.inventory.specification;

import com.nextstack.airetail.common.specification.BaseSpecifications;
import com.nextstack.airetail.inventory.dto.request.InventoryPageRequest;
import com.nextstack.airetail.inventory.entity.Inventory;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for inventory queries.
 */
public final class InventorySpecifications {

    private InventorySpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<Inventory> fromRequest(InventoryPageRequest request) {
        return Specification.where(BaseSpecifications.<Inventory>isActive())
                .and(branchIdEquals(request.getBranchId()))
                .and(productIdEquals(request.getProductId()))
                .and(lowStockOnly(request.getLowStockOnly()));
    }

    private static Specification<Inventory> branchIdEquals(Long branchId) {
        return (root, query, cb) -> {
            if (branchId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("branch").get("id"), branchId);
        };
    }

    private static Specification<Inventory> productIdEquals(Long productId) {
        return (root, query, cb) -> {
            if (productId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("product").get("id"), productId);
        };
    }

    private static Specification<Inventory> lowStockOnly(Boolean lowStockOnly) {
        return (root, query, cb) -> {
            if (!Boolean.TRUE.equals(lowStockOnly)) {
                return cb.conjunction();
            }
            return cb.and(
                    cb.isNotNull(root.get("reorderLevel")),
                    cb.lessThanOrEqualTo(root.get("quantity"), root.get("reorderLevel")));
        };
    }
}
