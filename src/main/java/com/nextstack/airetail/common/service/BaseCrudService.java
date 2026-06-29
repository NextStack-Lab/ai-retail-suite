package com.nextstack.airetail.common.service;

import com.nextstack.airetail.common.dto.PageRequestDto;
import com.nextstack.airetail.common.dto.PageResponse;
import org.springframework.data.domain.Page;

/**
 * Generic CRUD service contract for domain modules.
 *
 * @param <REQ>  create/update request DTO type
 * @param <RES>  response DTO type
 * @param <ID>   identifier type
 */
public interface BaseCrudService<REQ, RES, ID> {

    RES create(REQ request);

    RES getById(ID id);

    PageResponse<RES> getAll(PageRequestDto pageRequest);

    RES update(ID id, REQ request);

    void delete(ID id);
}
