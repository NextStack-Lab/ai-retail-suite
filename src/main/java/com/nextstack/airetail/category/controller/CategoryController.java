package com.nextstack.airetail.category.controller;

import com.nextstack.airetail.category.dto.request.CategoryPageRequest;
import com.nextstack.airetail.category.dto.request.CategoryRequest;
import com.nextstack.airetail.category.dto.response.CategoryResponse;
import com.nextstack.airetail.category.service.CategoryService;
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
 * REST controller for category management.
 */
@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Creates the controller with category service dependency.
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('PRODUCT_WRITE')")
    public ApiResponse<CategoryResponse> create(@Valid @RequestBody CategoryRequest request,
                                                HttpServletRequest httpRequest) {
        return ApiResponse.success("Category created successfully",
                categoryService.create(request), httpRequest.getRequestURI());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ApiResponse<CategoryResponse> getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        return ApiResponse.success("Category retrieved successfully",
                categoryService.getById(id), httpRequest.getRequestURI());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ApiResponse<PageResponse<CategoryResponse>> getAll(CategoryPageRequest pageRequest,
                                                              HttpServletRequest httpRequest) {
        return ApiResponse.success("Categories retrieved successfully",
                categoryService.getAll(pageRequest), httpRequest.getRequestURI());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_WRITE')")
    public ApiResponse<CategoryResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody CategoryRequest request,
                                                  HttpServletRequest httpRequest) {
        return ApiResponse.success("Category updated successfully",
                categoryService.update(id, request), httpRequest.getRequestURI());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_WRITE')")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        categoryService.delete(id);
        return ApiResponse.success("Category deleted successfully", httpRequest.getRequestURI());
    }
}
