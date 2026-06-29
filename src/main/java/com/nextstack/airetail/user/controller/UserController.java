package com.nextstack.airetail.user.controller;

import com.nextstack.airetail.common.dto.ApiResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.user.dto.request.UserPageRequest;
import com.nextstack.airetail.user.dto.request.UserRequest;
import com.nextstack.airetail.user.dto.response.UserResponse;
import com.nextstack.airetail.user.service.UserService;
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
 * REST controller for user management.
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * Creates the controller with user service dependency.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ApiResponse<UserResponse> create(@Valid @RequestBody UserRequest request,
                                            HttpServletRequest httpRequest) {
        return ApiResponse.success("User created successfully",
                userService.create(request), httpRequest.getRequestURI());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ApiResponse<UserResponse> getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        return ApiResponse.success("User retrieved successfully",
                userService.getById(id), httpRequest.getRequestURI());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    public ApiResponse<PageResponse<UserResponse>> getAll(UserPageRequest pageRequest,
                                                          HttpServletRequest httpRequest) {
        return ApiResponse.success("Users retrieved successfully",
                userService.getAll(pageRequest), httpRequest.getRequestURI());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ApiResponse<UserResponse> update(@PathVariable Long id,
                                            @Valid @RequestBody UserRequest request,
                                            HttpServletRequest httpRequest) {
        return ApiResponse.success("User updated successfully",
                userService.update(id, request), httpRequest.getRequestURI());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        userService.delete(id);
        return ApiResponse.success("User deleted successfully", httpRequest.getRequestURI());
    }
}
