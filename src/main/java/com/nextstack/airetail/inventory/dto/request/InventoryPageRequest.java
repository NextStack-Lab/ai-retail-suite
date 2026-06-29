package com.nextstack.airetail.inventory.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Paginated inventory list request with optional search filters.
 */
@Getter
@Setter
public class InventoryPageRequest extends PageRequestDto {

    private Long branchId;
    private Long productId;
    private Boolean lowStockOnly;
}
