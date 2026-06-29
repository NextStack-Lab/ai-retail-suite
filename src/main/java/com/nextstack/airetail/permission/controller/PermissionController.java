package com.nextstack.airetail.permission.controller;

import com.nextstack.airetail.common.dto.ApiResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.permission.dto.request.PermissionPageRequest;
import com.nextstack.airetail.permission.dto.response.PermissionResponse;
import com.nextstack.airetail.permission.service.PermissionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for permission read operations.
 */
@RestController
@RequestMapping("/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * Creates the controller with permission service dependency.
     */
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public ApiResponse<PermissionResponse> getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        return ApiResponse.success("Permission retrieved successfully",
                permissionService.getById(id), httpRequest.getRequestURI());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_WRITE')")
    public ApiResponse<PageResponse<PermissionResponse>> getAll(PermissionPageRequest pageRequest,
                                                                HttpServletRequest httpRequest) {
        return ApiResponse.success("Permissions retrieved successfully",
                permissionService.getAll(pageRequest), httpRequest.getRequestURI());
    }
}
