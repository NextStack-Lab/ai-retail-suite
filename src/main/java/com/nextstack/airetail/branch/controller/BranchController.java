package com.nextstack.airetail.branch.controller;

import com.nextstack.airetail.branch.dto.request.BranchPageRequest;
import com.nextstack.airetail.branch.dto.request.BranchRequest;
import com.nextstack.airetail.branch.dto.response.BranchResponse;
import com.nextstack.airetail.branch.service.BranchService;
import com.nextstack.airetail.common.dto.ApiResponse;
import com.nextstack.airetail.common.dto.PageResponse;
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
 * REST controller for branch management.
 */
@RestController
@RequestMapping("/v1/branches")
public class BranchController {

    private final BranchService branchService;

    /**
     * Creates the controller with branch service dependency.
     */
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('BRANCH_WRITE')")
    public ApiResponse<BranchResponse> create(@Valid @RequestBody BranchRequest request,
                                              HttpServletRequest httpRequest) {
        return ApiResponse.success("Branch created successfully",
                branchService.create(request), httpRequest.getRequestURI());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('BRANCH_READ')")
    public ApiResponse<BranchResponse> getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        return ApiResponse.success("Branch retrieved successfully",
                branchService.getById(id), httpRequest.getRequestURI());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('BRANCH_READ')")
    public ApiResponse<PageResponse<BranchResponse>> getAll(BranchPageRequest pageRequest,
                                                              HttpServletRequest httpRequest) {
        return ApiResponse.success("Branches retrieved successfully",
                branchService.getAll(pageRequest), httpRequest.getRequestURI());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('BRANCH_WRITE')")
    public ApiResponse<BranchResponse> update(@PathVariable Long id,
                                              @Valid @RequestBody BranchRequest request,
                                              HttpServletRequest httpRequest) {
        return ApiResponse.success("Branch updated successfully",
                branchService.update(id, request), httpRequest.getRequestURI());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('BRANCH_WRITE')")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        branchService.delete(id);
        return ApiResponse.success("Branch deleted successfully", httpRequest.getRequestURI());
    }
}
