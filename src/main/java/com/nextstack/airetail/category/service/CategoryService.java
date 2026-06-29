package com.nextstack.airetail.category.service;

import com.nextstack.airetail.category.dto.request.CategoryPageRequest;
import com.nextstack.airetail.category.dto.request.CategoryRequest;
import com.nextstack.airetail.category.dto.response.CategoryResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.BaseCrudService;

/**
 * Service contract for category management.
 */
public interface CategoryService extends BaseCrudService<CategoryRequest, CategoryResponse, Long> {

    /**
     * Returns paginated categories with optional filters.
     *
     * @param pageRequest page and filter parameters
     * @return paginated category responses
     */
    PageResponse<CategoryResponse> getAll(CategoryPageRequest pageRequest);
}
