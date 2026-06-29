package com.nextstack.airetail.permission.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Paginated permission list request with optional search filters.
 */
@Getter
@Setter
public class PermissionPageRequest extends PageRequestDto {

    private String search;
    private String resource;
}
