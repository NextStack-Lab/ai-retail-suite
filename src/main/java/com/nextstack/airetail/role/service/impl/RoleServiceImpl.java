package com.nextstack.airetail.role.service.impl;

import com.nextstack.airetail.common.dto.PageRequestDto;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.common.service.PageResponseMapper;
import com.nextstack.airetail.company.entity.Company;
import com.nextstack.airetail.company.repository.CompanyRepository;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import com.nextstack.airetail.permission.entity.Permission;
import com.nextstack.airetail.permission.repository.PermissionRepository;
import com.nextstack.airetail.role.dto.request.RolePageRequest;
import com.nextstack.airetail.role.dto.request.RoleRequest;
import com.nextstack.airetail.role.dto.response.RoleResponse;
import com.nextstack.airetail.role.entity.Role;
import com.nextstack.airetail.role.mapper.RoleMapper;
import com.nextstack.airetail.role.repository.RoleRepository;
import com.nextstack.airetail.role.service.RoleService;
import com.nextstack.airetail.role.specification.RoleSpecifications;
import com.nextstack.airetail.role.validator.RoleValidator;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link RoleService}.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;
    private final RoleValidator roleValidator;

    /**
     * Creates the service with required dependencies.
     */
    public RoleServiceImpl(RoleRepository roleRepository,
                           CompanyRepository companyRepository,
                           PermissionRepository permissionRepository,
                           RoleMapper roleMapper,
                           RoleValidator roleValidator) {
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
        this.permissionRepository = permissionRepository;
        this.roleMapper = roleMapper;
        this.roleValidator = roleValidator;
    }

    @Override
    public RoleResponse create(RoleRequest request) {
        roleValidator.validateCreate(request);
        Role role = roleMapper.toEntity(request);
        role.setCompany(findCompanyOptional(request.getCompanyId()));
        role.setPermissions(resolvePermissions(request.getPermissionIds()));
        role.setActive(true);
        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getById(Long id) {
        return roleMapper.toResponse(findActiveById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<RoleResponse> getAll(PageRequestDto pageRequest) {
        RolePageRequest request = new RolePageRequest();
        request.setPage(pageRequest.getPage());
        request.setSize(pageRequest.getSize());
        request.setSortBy(pageRequest.getSortBy());
        request.setSortDirection(pageRequest.getSortDirection());
        return getAll(request);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<RoleResponse> getAll(RolePageRequest pageRequest) {
        Page<Role> page = roleRepository.findAll(
                RoleSpecifications.fromRequest(pageRequest),
                pageRequest.toPageable());
        return PageResponseMapper.toPageResponse(page, roleMapper::toResponse);
    }

    @Override
    public RoleResponse update(Long id, RoleRequest request) {
        Role role = findActiveById(id);
        roleValidator.validateUpdate(id, request);
        roleMapper.updateEntity(request, role);
        role.setCompany(findCompanyOptional(request.getCompanyId()));
        if (request.getPermissionIds() != null) {
            role.setPermissions(resolvePermissions(request.getPermissionIds()));
        }
        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public void delete(Long id) {
        Role role = findActiveById(id);
        role.setActive(false);
        roleRepository.save(role);
    }

    private Role findActiveById(Long id) {
        return roleRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
    }

    private Company findCompanyOptional(Long companyId) {
        if (companyId == null) {
            return null;
        }
        return companyRepository.findByIdAndActiveTrue(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));
    }

    private Set<Permission> resolvePermissions(Set<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return new HashSet<>();
        }
        Set<Permission> permissions = new HashSet<>();
        for (Long permissionId : permissionIds) {
            Permission permission = permissionRepository.findByIdAndActiveTrue(permissionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", permissionId));
            permissions.add(permission);
        }
        return permissions;
    }
}
