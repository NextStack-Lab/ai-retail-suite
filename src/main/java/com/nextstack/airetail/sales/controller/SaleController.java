package com.nextstack.airetail.sales.controller;

import com.nextstack.airetail.common.dto.ApiResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.sales.dto.request.SalePageRequest;
import com.nextstack.airetail.sales.dto.request.SaleRequest;
import com.nextstack.airetail.sales.dto.response.SaleResponse;
import com.nextstack.airetail.sales.service.SaleService;
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
 * REST controller for sale management.
 */
@RestController
@RequestMapping("/v1/sales")
public class SaleController {

    private final SaleService saleService;

    /**
     * Creates the controller with sale service dependency.
     */
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SALE_WRITE')")
    public ApiResponse<SaleResponse> create(@Valid @RequestBody SaleRequest request,
                                            HttpServletRequest httpRequest) {
        return ApiResponse.success("Sale created successfully",
                saleService.create(request), httpRequest.getRequestURI());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SALE_READ')")
    public ApiResponse<SaleResponse> getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        return ApiResponse.success("Sale retrieved successfully",
                saleService.getById(id), httpRequest.getRequestURI());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SALE_READ')")
    public ApiResponse<PageResponse<SaleResponse>> getAll(SalePageRequest pageRequest,
                                                            HttpServletRequest httpRequest) {
        return ApiResponse.success("Sales retrieved successfully",
                saleService.getAll(pageRequest), httpRequest.getRequestURI());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SALE_WRITE')")
    public ApiResponse<SaleResponse> update(@PathVariable Long id,
                                            @Valid @RequestBody SaleRequest request,
                                            HttpServletRequest httpRequest) {
        return ApiResponse.success("Sale updated successfully",
                saleService.update(id, request), httpRequest.getRequestURI());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SALE_WRITE')")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        saleService.delete(id);
        return ApiResponse.success("Sale deleted successfully", httpRequest.getRequestURI());
    }
}
