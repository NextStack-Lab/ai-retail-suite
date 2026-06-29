package com.nextstack.airetail.branch.service;

import com.nextstack.airetail.branch.dto.request.BranchPageRequest;
import com.nextstack.airetail.branch.dto.request.BranchRequest;
import com.nextstack.airetail.branch.dto.response.BranchResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.BaseCrudService;

/**
 * Service contract for branch management.
 */
public interface BranchService extends BaseCrudService<BranchRequest, BranchResponse, Long> {

    /**
     * Returns paginated branches with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated branch responses
     */
    PageResponse<BranchResponse> getAll(BranchPageRequest pageRequest);
}
