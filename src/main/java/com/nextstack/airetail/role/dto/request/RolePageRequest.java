package com.nextstack.airetail.role.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Paginated role list request with optional search filters.
 */
@Getter
@Setter
public class RolePageRequest extends PageRequestDto {

    private String search;
    private Long companyId;
}
