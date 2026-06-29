package com.nextstack.airetail.product.controller;

import com.nextstack.airetail.common.dto.ApiResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.product.dto.request.ProductPageRequest;
import com.nextstack.airetail.product.dto.request.ProductRequest;
import com.nextstack.airetail.product.dto.response.ProductResponse;
import com.nextstack.airetail.product.service.ProductService;
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
 * REST controller for product management.
 */
@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Creates the controller with product service dependency.
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('PRODUCT_WRITE')")
    public ApiResponse<ProductResponse> create(@Valid @RequestBody ProductRequest request,
                                               HttpServletRequest httpRequest) {
        return ApiResponse.success("Product created successfully",
                productService.create(request), httpRequest.getRequestURI());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ApiResponse<ProductResponse> getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        return ApiResponse.success("Product retrieved successfully",
                productService.getById(id), httpRequest.getRequestURI());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ApiResponse<PageResponse<ProductResponse>> getAll(ProductPageRequest pageRequest,
                                                               HttpServletRequest httpRequest) {
        return ApiResponse.success("Products retrieved successfully",
                productService.getAll(pageRequest), httpRequest.getRequestURI());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_WRITE')")
    public ApiResponse<ProductResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody ProductRequest request,
                                               HttpServletRequest httpRequest) {
        return ApiResponse.success("Product updated successfully",
                productService.update(id, request), httpRequest.getRequestURI());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_WRITE')")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        productService.delete(id);
        return ApiResponse.success("Product deleted successfully", httpRequest.getRequestURI());
    }
}
