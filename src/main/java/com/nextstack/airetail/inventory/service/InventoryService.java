package com.nextstack.airetail.inventory.service;

import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.BaseCrudService;
import com.nextstack.airetail.inventory.dto.request.InventoryPageRequest;
import com.nextstack.airetail.inventory.dto.request.InventoryRequest;
import com.nextstack.airetail.inventory.dto.response.InventoryResponse;

/**
 * Service contract for inventory management.
 */
public interface InventoryService extends BaseCrudService<InventoryRequest, InventoryResponse, Long> {

    /**
     * Returns paginated inventory records with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated inventory responses
     */
    PageResponse<InventoryResponse> getAll(InventoryPageRequest pageRequest);
}
