package com.nextstack.airetail.sales.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import com.nextstack.airetail.sales.entity.SaleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Paginated sale list request with optional search filters.
 */
@Getter
@Setter
public class SalePageRequest extends PageRequestDto {

    private String search;
    private Long branchId;
    private Long customerId;
    private SaleStatus status;
    private Instant startDate;
    private Instant endDate;
}
