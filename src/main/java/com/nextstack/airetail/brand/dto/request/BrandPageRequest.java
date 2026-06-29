package com.nextstack.airetail.brand.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Paginated brand list request with optional search filters.
 */
@Getter
@Setter
public class BrandPageRequest extends PageRequestDto {

    private String search;
    private Long companyId;
}
