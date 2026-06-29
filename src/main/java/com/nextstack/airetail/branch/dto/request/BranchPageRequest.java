package com.nextstack.airetail.branch.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Paginated branch list request with optional search filters.
 */
@Getter
@Setter
public class BranchPageRequest extends PageRequestDto {

    private String search;
    private Long companyId;
}
