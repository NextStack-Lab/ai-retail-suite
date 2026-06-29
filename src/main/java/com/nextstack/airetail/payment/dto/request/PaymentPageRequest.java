package com.nextstack.airetail.payment.dto.request;

import com.nextstack.airetail.common.dto.PageRequestDto;
import com.nextstack.airetail.payment.entity.PaymentMethod;
import com.nextstack.airetail.payment.entity.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Paginated payment list request with optional search filters.
 */
@Getter
@Setter
public class PaymentPageRequest extends PageRequestDto {

    private Long saleId;
    private PaymentMethod method;
    private PaymentStatus status;
}
