package com.nextstack.airetail.role.service;

import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.BaseCrudService;
import com.nextstack.airetail.role.dto.request.RolePageRequest;
import com.nextstack.airetail.role.dto.request.RoleRequest;
import com.nextstack.airetail.role.dto.response.RoleResponse;

/**
 * Service contract for role management.
 */
public interface RoleService extends BaseCrudService<RoleRequest, RoleResponse, Long> {

    /**
     * Returns paginated roles with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated role responses
     */
    PageResponse<RoleResponse> getAll(RolePageRequest pageRequest);
}
