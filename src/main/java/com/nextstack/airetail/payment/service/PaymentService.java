package com.nextstack.airetail.payment.service;

import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.BaseCrudService;
import com.nextstack.airetail.payment.dto.request.PaymentPageRequest;
import com.nextstack.airetail.payment.dto.request.PaymentRequest;
import com.nextstack.airetail.payment.dto.response.PaymentResponse;

/**
 * Service contract for payment management.
 */
public interface PaymentService extends BaseCrudService<PaymentRequest, PaymentResponse, Long> {

    /**
     * Returns paginated payments with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated payment responses
     */
    PageResponse<PaymentResponse> getAll(PaymentPageRequest pageRequest);
}
