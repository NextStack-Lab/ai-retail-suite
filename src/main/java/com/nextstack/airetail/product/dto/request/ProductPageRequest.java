package com.nextstack.airetail.product.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Paginated product list request with optional search filters.
 */
@Getter
@Setter
public class ProductPageRequest extends PageRequestDto {

    private String search;
    private Long companyId;
    private Long categoryId;
    private Long brandId;
}
