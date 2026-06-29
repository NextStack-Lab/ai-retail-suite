package com.nextstack.airetail.brand.controller;

import com.nextstack.airetail.brand.dto.request.BrandPageRequest;
import com.nextstack.airetail.brand.dto.request.BrandRequest;
import com.nextstack.airetail.brand.dto.response.BrandResponse;
import com.nextstack.airetail.brand.service.BrandService;
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
 * REST controller for brand management.
 */
@RestController
@RequestMapping("/v1/brands")
public class BrandController {

    private final BrandService brandService;

    /**
     * Creates the controller with brand service dependency.
     */
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('PRODUCT_WRITE')")
    public ApiResponse<BrandResponse> create(@Valid @RequestBody BrandRequest request,
                                             HttpServletRequest httpRequest) {
        return ApiResponse.success("Brand created successfully",
                brandService.create(request), httpRequest.getRequestURI());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ApiResponse<BrandResponse> getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        return ApiResponse.success("Brand retrieved successfully",
                brandService.getById(id), httpRequest.getRequestURI());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ApiResponse<PageResponse<BrandResponse>> getAll(BrandPageRequest pageRequest,
                                                             HttpServletRequest httpRequest) {
        return ApiResponse.success("Brands retrieved successfully",
                brandService.getAll(pageRequest), httpRequest.getRequestURI());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_WRITE')")
    public ApiResponse<BrandResponse> update(@PathVariable Long id,
                                             @Valid @RequestBody BrandRequest request,
                                             HttpServletRequest httpRequest) {
        return ApiResponse.success("Brand updated successfully",
                brandService.update(id, request), httpRequest.getRequestURI());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_WRITE')")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        brandService.delete(id);
        return ApiResponse.success("Brand deleted successfully", httpRequest.getRequestURI());
    }
}
