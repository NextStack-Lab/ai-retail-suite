package com.nextstack.airetail.payment.dto.response;

import com.nextstack.airetail.payment.entity.PaymentMethod;
import com.nextstack.airetail.payment.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Response DTO for payment data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long id;
    private PaymentMethod method;
    private BigDecimal amount;
    private String reference;
    private PaymentStatus status;
    private Long saleId;
    private String saleNumber;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
