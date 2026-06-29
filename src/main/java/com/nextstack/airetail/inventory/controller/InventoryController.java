package com.nextstack.airetail.inventory.controller;

import com.nextstack.airetail.common.dto.ApiResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.inventory.dto.request.InventoryPageRequest;
import com.nextstack.airetail.inventory.dto.request.InventoryRequest;
import com.nextstack.airetail.inventory.dto.response.InventoryResponse;
import com.nextstack.airetail.inventory.service.InventoryService;
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
 * REST controller for inventory management.
 */
@RestController
@RequestMapping("/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Creates the controller with inventory service dependency.
     */
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ApiResponse<InventoryResponse> create(@Valid @RequestBody InventoryRequest request,
                                                 HttpServletRequest httpRequest) {
        return ApiResponse.success("Inventory record created successfully",
                inventoryService.create(request), httpRequest.getRequestURI());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ApiResponse<InventoryResponse> getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        return ApiResponse.success("Inventory record retrieved successfully",
                inventoryService.getById(id), httpRequest.getRequestURI());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public ApiResponse<PageResponse<InventoryResponse>> getAll(InventoryPageRequest pageRequest,
                                                                 HttpServletRequest httpRequest) {
        return ApiResponse.success("Inventory records retrieved successfully",
                inventoryService.getAll(pageRequest), httpRequest.getRequestURI());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ApiResponse<InventoryResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody InventoryRequest request,
                                                 HttpServletRequest httpRequest) {
        return ApiResponse.success("Inventory record updated successfully",
                inventoryService.update(id, request), httpRequest.getRequestURI());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('INVENTORY_WRITE')")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        inventoryService.delete(id);
        return ApiResponse.success("Inventory record deleted successfully", httpRequest.getRequestURI());
    }
}
