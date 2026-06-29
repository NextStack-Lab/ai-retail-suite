package com.nextstack.airetail.customer.specification;

import com.nextstack.airetail.common.specification.BaseSpecifications;
import com.nextstack.airetail.customer.dto.request.CustomerPageRequest;
import com.nextstack.airetail.customer.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for customer queries.
 */
public final class CustomerSpecifications {

    private CustomerSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<Customer> fromRequest(CustomerPageRequest request) {
        return Specification.where(BaseSpecifications.<Customer>isActive())
                .and(searchFilter(request.getSearch()))
                .and(companyIdEquals(request.getCompanyId()));
    }

    private static Specification<Customer> searchFilter(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return cb.conjunction();
            }
            String pattern = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("email")), pattern),
                    cb.like(cb.lower(root.get("phone")), pattern));
        };
    }

    private static Specification<Customer> companyIdEquals(Long companyId) {
        return (root, query, cb) -> {
            if (companyId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("company").get("id"), companyId);
        };
    }
}
