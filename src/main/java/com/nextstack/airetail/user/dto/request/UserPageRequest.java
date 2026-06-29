package com.nextstack.airetail.user.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Paginated user list request with optional search filters.
 */
@Getter
@Setter
public class UserPageRequest extends PageRequestDto {

    private String search;
    private Long companyId;
    private Long branchId;
}
