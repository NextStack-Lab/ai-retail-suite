package com.nextstack.airetail.permission.service;

import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.permission.dto.request.PermissionPageRequest;
import com.nextstack.airetail.permission.dto.response.PermissionResponse;

/**
 * Service contract for permission read operations.
 */
public interface PermissionService {

    /**
     * Returns a permission by identifier.
     *
     * @param id permission identifier
     * @return permission response
     */
    PermissionResponse getById(Long id);

    /**
     * Returns paginated permissions with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated permission responses
     */
    PageResponse<PermissionResponse> getAll(PermissionPageRequest pageRequest);
}
