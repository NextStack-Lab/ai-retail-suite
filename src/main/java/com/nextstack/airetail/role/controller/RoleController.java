package com.nextstack.airetail.role.controller;

import com.nextstack.airetail.common.dto.ApiResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.role.dto.request.RolePageRequest;
import com.nextstack.airetail.role.dto.request.RoleRequest;
import com.nextstack.airetail.role.dto.response.RoleResponse;
import com.nextstack.airetail.role.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for role management.
 */
@RestController
@RequestMapping("/v1/roles")
public class RoleController {

    private final RoleService roleService;

    /**
     * Creates the controller with role service dependency.
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public ApiResponse<RoleResponse> create(@Valid @RequestBody RoleRequest request,
                                            HttpServletRequest httpRequest) {
        return ApiResponse.success("Role created successfully",
                roleService.create(request), httpRequest.getRequestURI());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public ApiResponse<RoleResponse> getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        return ApiResponse.success("Role retrieved successfully",
                roleService.getById(id), httpRequest.getRequestURI());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public ApiResponse<PageResponse<RoleResponse>> getAll(RolePageRequest pageRequest,
                                                          HttpServletRequest httpRequest) {
        return ApiResponse.success("Roles retrieved successfully",
                roleService.getAll(pageRequest), httpRequest.getRequestURI());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public ApiResponse<RoleResponse> update(@PathVariable Long id,
                                            @Valid @RequestBody RoleRequest request,
                                            HttpServletRequest httpRequest) {
        return ApiResponse.success("Role updated successfully",
                roleService.update(id, request), httpRequest.getRequestURI());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        roleService.delete(id);
        return ApiResponse.success("Role deleted successfully", httpRequest.getRequestURI());
    }
}
