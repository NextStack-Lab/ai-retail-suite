package com.nextstack.airetail.payment.specification;

import com.nextstack.airetail.common.specification.BaseSpecifications;
import com.nextstack.airetail.payment.dto.request.PaymentPageRequest;
import com.nextstack.airetail.payment.entity.Payment;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for payment queries.
 */
public final class PaymentSpecifications {

    private PaymentSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<Payment> fromRequest(PaymentPageRequest request) {
        return Specification.where(BaseSpecifications.<Payment>isActive())
                .and(saleIdEquals(request.getSaleId()))
                .and(methodEquals(request.getMethod()))
                .and(statusEquals(request.getStatus()));
    }

    private static Specification<Payment> saleIdEquals(Long saleId) {
        return (root, query, cb) -> {
            if (saleId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("sale").get("id"), saleId);
        };
    }

    private static Specification<Payment> methodEquals(com.nextstack.airetail.payment.entity.PaymentMethod method) {
        return (root, query, cb) -> {
            if (method == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("method"), method);
        };
    }

    private static Specification<Payment> statusEquals(com.nextstack.airetail.payment.entity.PaymentStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("status"), status);
        };
    }
}
