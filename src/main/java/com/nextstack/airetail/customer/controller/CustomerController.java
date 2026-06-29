package com.nextstack.airetail.customer.controller;

import com.nextstack.airetail.common.dto.ApiResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.customer.dto.request.CustomerPageRequest;
import com.nextstack.airetail.customer.dto.request.CustomerRequest;
import com.nextstack.airetail.customer.dto.response.CustomerResponse;
import com.nextstack.airetail.customer.service.CustomerService;
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
 * REST controller for customer management.
 */
@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Creates the controller with customer service dependency.
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SALE_WRITE')")
    public ApiResponse<CustomerResponse> create(@Valid @RequestBody CustomerRequest request,
                                                HttpServletRequest httpRequest) {
        return ApiResponse.success("Customer created successfully",
                customerService.create(request), httpRequest.getRequestURI());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SALE_READ')")
    public ApiResponse<CustomerResponse> getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        return ApiResponse.success("Customer retrieved successfully",
                customerService.getById(id), httpRequest.getRequestURI());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SALE_READ')")
    public ApiResponse<PageResponse<CustomerResponse>> getAll(CustomerPageRequest pageRequest,
                                                                  HttpServletRequest httpRequest) {
        return ApiResponse.success("Customers retrieved successfully",
                customerService.getAll(pageRequest), httpRequest.getRequestURI());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SALE_WRITE')")
    public ApiResponse<CustomerResponse> update(@PathVariable Long id,
                                                @Valid @RequestBody CustomerRequest request,
                                                HttpServletRequest httpRequest) {
        return ApiResponse.success("Customer updated successfully",
                customerService.update(id, request), httpRequest.getRequestURI());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SALE_WRITE')")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        customerService.delete(id);
        return ApiResponse.success("Customer deleted successfully", httpRequest.getRequestURI());
    }
}
