package com.nextstack.airetail.permission.service.impl;

import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.PageResponseMapper;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import com.nextstack.airetail.permission.dto.request.PermissionPageRequest;
import com.nextstack.airetail.permission.dto.response.PermissionResponse;
import com.nextstack.airetail.permission.entity.Permission;
import com.nextstack.airetail.permission.mapper.PermissionMapper;
import com.nextstack.airetail.permission.repository.PermissionRepository;
import com.nextstack.airetail.permission.service.PermissionService;
import com.nextstack.airetail.permission.specification.PermissionSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link PermissionService}.
 */
@Service
@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    /**
     * Creates the service with required dependencies.
     */
    public PermissionServiceImpl(PermissionRepository permissionRepository,
                                 PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public PermissionResponse getById(Long id) {
        return permissionMapper.toResponse(findActiveById(id));
    }

    @Override
    public PageResponse<PermissionResponse> getAll(PermissionPageRequest pageRequest) {
        Page<Permission> page = permissionRepository.findAll(
                PermissionSpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, permissionMapper::toResponse);
    }

    private Permission findActiveById(Long id) {
        return permissionRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));
    }
}
