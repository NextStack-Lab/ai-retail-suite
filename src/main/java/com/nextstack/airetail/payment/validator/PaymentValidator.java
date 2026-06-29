package com.nextstack.airetail.payment.validator;

import com.nextstack.airetail.exception.BusinessException;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import com.nextstack.airetail.payment.dto.request.PaymentRequest;
import com.nextstack.airetail.sales.entity.Sale;
import com.nextstack.airetail.sales.repository.SaleRepository;
import org.springframework.stereotype.Component;

/**
 * Business validation rules for payment operations.
 */
@Component
public class PaymentValidator {

    private final SaleRepository saleRepository;

    /**
     * Creates the validator with required dependencies.
     */
    public PaymentValidator(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    /**
     * Validates a create request.
     *
     * @param request payment request
     */
    public void validateCreate(PaymentRequest request) {
        Sale sale = saleRepository.findByIdAndActiveTrue(request.getSaleId())
                .orElseThrow(() -> new ResourceNotFoundException("Sale", "id", request.getSaleId()));
        if (request.getAmount().compareTo(sale.getTotal()) > 0) {
            throw new BusinessException("Payment amount cannot exceed sale total");
        }
    }

    /**
     * Validates an update request.
     *
     * @param id      payment identifier
     * @param request payment request
     */
    public void validateUpdate(Long id, PaymentRequest request) {
        validateCreate(request);
    }
}
