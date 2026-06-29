package com.nextstack.airetail.category.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Paginated category list request with optional search filters.
 */
@Getter
@Setter
public class CategoryPageRequest extends PageRequestDto {

    private String search;
    private Long companyId;
    private Long parentId;
}
