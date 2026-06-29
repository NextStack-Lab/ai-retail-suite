package com.nextstack.airetail.payment.controller;

import com.nextstack.airetail.common.dto.ApiResponse;
import com.nextstack.airetail.common.dto.PageResponse;
import com.nextstack.airetail.payment.dto.request.PaymentPageRequest;
import com.nextstack.airetail.payment.dto.request.PaymentRequest;
import com.nextstack.airetail.payment.dto.response.PaymentResponse;
import com.nextstack.airetail.payment.service.PaymentService;
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
 * REST controller for payment management.
 */
@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Creates the controller with payment service dependency.
     */
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SALE_WRITE')")
    public ApiResponse<PaymentResponse> create(@Valid @RequestBody PaymentRequest request,
                                               HttpServletRequest httpRequest) {
        return ApiResponse.success("Payment created successfully",
                paymentService.create(request), httpRequest.getRequestURI());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SALE_READ')")
    public ApiResponse<PaymentResponse> getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        return ApiResponse.success("Payment retrieved successfully",
                paymentService.getById(id), httpRequest.getRequestURI());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SALE_READ')")
    public ApiResponse<PageResponse<PaymentResponse>> getAll(PaymentPageRequest pageRequest,
                                                               HttpServletRequest httpRequest) {
        return ApiResponse.success("Payments retrieved successfully",
                paymentService.getAll(pageRequest), httpRequest.getRequestURI());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SALE_WRITE')")
    public ApiResponse<PaymentResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody PaymentRequest request,
                                               HttpServletRequest httpRequest) {
        return ApiResponse.success("Payment updated successfully",
                paymentService.update(id, request), httpRequest.getRequestURI());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SALE_WRITE')")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
        paymentService.delete(id);
        return ApiResponse.success("Payment deleted successfully", httpRequest.getRequestURI());
    }
}
