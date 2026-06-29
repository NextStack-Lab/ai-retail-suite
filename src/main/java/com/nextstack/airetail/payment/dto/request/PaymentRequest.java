package com.nextstack.airetail.payment.dto.request;

import com.nextstack.airetail.payment.entity.PaymentMethod;
import com.nextstack.airetail.payment.entity.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Request DTO for creating or updating a payment.
 */
@Getter
@Setter
public class PaymentRequest {

    @NotNull(message = "Payment method is required")
    private PaymentMethod method;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    @Size(max = 200)
    private String reference;

    private PaymentStatus status;

    @NotNull(message = "Sale ID is required")
    private Long saleId;
}
