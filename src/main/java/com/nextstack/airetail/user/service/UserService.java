package com.nextstack.airetail.user.service;

import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.BaseCrudService;
import com.nextstack.airetail.user.dto.request.UserPageRequest;
import com.nextstack.airetail.user.dto.request.UserRequest;
import com.nextstack.airetail.user.dto.response.UserResponse;

/**
 * Service contract for user management.
 */
public interface UserService extends BaseCrudService<UserRequest, UserResponse, Long> {

    /**
     * Returns paginated users with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated user responses
     */
    PageResponse<UserResponse> getAll(UserPageRequest pageRequest);
}
