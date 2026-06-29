package com.nextstack.airetail.common.dto;

import com.nextstack.airetail.common.constants.AppConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Base pagination and sorting request parameters.
 */
@Getter
@Setter
public class PageRequestDto {

    private int page = Integer.parseInt(AppConstants.DEFAULT_PAGE_NUMBER);
    private int size = Integer.parseInt(AppConstants.DEFAULT_PAGE_SIZE);
    private String sortBy = "id";
    private String sortDirection = AppConstants.DEFAULT_SORT_DIRECTION;

    /**
     * Converts DTO parameters to Spring {@link Pageable}.
     *
     * @return pageable instance with sorting applied
     */
    public Pageable toPageable() {
        int pageSize = Math.min(Math.max(size, 1), AppConstants.MAX_PAGE_SIZE);
        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return PageRequest.of(Math.max(page, 0), pageSize, sort);
    }
}
