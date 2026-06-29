package com.nextstack.airetail.company.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Paginated company list request with optional search filters.
 */
@Getter
@Setter
public class CompanyPageRequest extends PageRequestDto {

    private String search;
    private String code;
}
