package com.nextstack.airetail.sales.validator;

import com.nextstack.airetail.branch.repository.BranchRepository;
import com.nextstack.airetail.customer.repository.CustomerRepository;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import com.nextstack.airetail.product.repository.ProductRepository;
import com.nextstack.airetail.sales.dto.request.SaleItemRequest;
import com.nextstack.airetail.sales.dto.request.SaleRequest;
import org.springframework.stereotype.Component;

/**
 * Business validation rules for sale operations.
 */
@Component
public class SaleValidator {

    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    /**
     * Creates the validator with required dependencies.
     */
    public SaleValidator(BranchRepository branchRepository,
                         CustomerRepository customerRepository,
                         ProductRepository productRepository) {
        this.branchRepository = branchRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    /**
     * Validates a create request.
     *
     * @param request sale request
     */
    public void validateCreate(SaleRequest request) {
        ensureBranchExists(request.getBranchId());
        if (request.getCustomerId() != null) {
            customerRepository.findByIdAndActiveTrue(request.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", request.getCustomerId()));
        }
        for (SaleItemRequest item : request.getItems()) {
            productRepository.findByIdAndActiveTrue(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", item.getProductId()));
        }
    }

    /**
     * Validates an update request.
     *
     * @param id      sale identifier
     * @param request sale request
     */
    public void validateUpdate(Long id, SaleRequest request) {
        validateCreate(request);
    }

    private void ensureBranchExists(Long branchId) {
        branchRepository.findByIdAndActiveTrue(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", branchId));
    }
}
