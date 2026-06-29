package com.nextstack.airetail.customer.service;

import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.BaseCrudService;
import com.nextstack.airetail.customer.dto.request.CustomerPageRequest;
import com.nextstack.airetail.customer.dto.request.CustomerRequest;
import com.nextstack.airetail.customer.dto.response.CustomerResponse;

/**
 * Service contract for customer management.
 */
public interface CustomerService extends BaseCrudService<CustomerRequest, CustomerResponse, Long> {

    /**
     * Returns paginated customers with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated customer responses
     */
    PageResponse<CustomerResponse> getAll(CustomerPageRequest pageRequest);
}
