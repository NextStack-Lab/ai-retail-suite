package com.nextstack.airetail.common.service;

import com.nextstack.airetail.common.dto.PageResponse;
import org.springframework.data.domain.Page;

/**
 * Utility methods for pagination mapping.
 */
public final class PageResponseMapper {

    private PageResponseMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Maps a Spring Data {@link Page} to {@link PageResponse}.
     *
     * @param page   source page
     * @param mapper function to map each entity to DTO
     * @param <E>    entity type
     * @param <D>    DTO type
     * @return paginated response
     */
    public static <E, D> PageResponse<D> toPageResponse(Page<E> page, java.util.function.Function<E, D> mapper) {
        return PageResponse.<D>builder()
                .content(page.getContent().stream().map(mapper).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
