package com.nextstack.airetail.company.service;

import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.BaseCrudService;
import com.nextstack.airetail.company.dto.request.CompanyPageRequest;
import com.nextstack.airetail.company.dto.request.CompanyRequest;
import com.nextstack.airetail.company.dto.response.CompanyResponse;

/**
 * Service contract for company management.
 */
public interface CompanyService extends BaseCrudService<CompanyRequest, CompanyResponse, Long> {

    @Override
    PageResponse<CompanyResponse> getAll(com.nextstack.airetail.common.dto.PageRequestDto pageRequest);

    /**
     * Returns paginated companies with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated company responses
     */
    PageResponse<CompanyResponse> getAll(CompanyPageRequest pageRequest);
}
