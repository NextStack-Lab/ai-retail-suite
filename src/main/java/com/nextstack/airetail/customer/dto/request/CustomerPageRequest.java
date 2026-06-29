package com.nextstack.airetail.customer.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Paginated customer list request with optional search filters.
 */
@Getter
@Setter
public class CustomerPageRequest extends PageRequestDto {

    private String search;
    private Long companyId;
}
