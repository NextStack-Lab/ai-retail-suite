package com.nextstack.airetail.product.service;

import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.BaseCrudService;
import com.nextstack.airetail.product.dto.request.ProductPageRequest;
import com.nextstack.airetail.product.dto.request.ProductRequest;
import com.nextstack.airetail.product.dto.response.ProductResponse;

/**
 * Service contract for product management.
 */
public interface ProductService extends BaseCrudService<ProductRequest, ProductResponse, Long> {

    /**
     * Returns paginated products with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated product responses
     */
    PageResponse<ProductResponse> getAll(ProductPageRequest pageRequest);
}
