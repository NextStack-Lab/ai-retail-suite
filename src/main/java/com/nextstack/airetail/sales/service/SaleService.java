package com.nextstack.airetail.sales.service;

import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.BaseCrudService;
import com.nextstack.airetail.sales.dto.request.SalePageRequest;
import com.nextstack.airetail.sales.dto.request.SaleRequest;
import com.nextstack.airetail.sales.dto.response.SaleResponse;

/**
 * Service contract for sale management.
 */
public interface SaleService extends BaseCrudService<SaleRequest, SaleResponse, Long> {

    /**
     * Returns paginated sales with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated sale responses
     */
    PageResponse<SaleResponse> getAll(SalePageRequest pageRequest);
}
