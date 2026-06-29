package com.nextstack.airetail.brand.service;

import com.nextstack.airetail.brand.dto.request.BrandPageRequest;
import com.nextstack.airetail.brand.dto.request.BrandRequest;
import com.nextstack.airetail.brand.dto.response.BrandResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.BaseCrudService;

/**
 * Service contract for brand management.
 */
public interface BrandService extends BaseCrudService<BrandRequest, BrandResponse, Long> {

    /**
     * Returns paginated brands with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated brand responses
     */
    PageResponse<BrandResponse> getAll(BrandPageRequest pageRequest);
}
