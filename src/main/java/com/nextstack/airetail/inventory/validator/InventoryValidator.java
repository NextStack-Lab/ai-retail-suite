package com.nextstack.airetail.inventory.validator;

import com.nextstack.airetail.branch.repository.BranchRepository;
import com.nextstack.airetail.exception.BusinessException;
import com.nextstack.airetail.exception.ResourceNotFoundException;
import com.nextstack.airetail.inventory.dto.request.InventoryRequest;
import com.nextstack.airetail.inventory.repository.InventoryRepository;
import com.nextstack.airetail.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

/**
 * Business validation rules for inventory operations.
 */
@Component
public class InventoryValidator {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    /**
     * Creates the validator with required dependencies.
     */
    public InventoryValidator(InventoryRepository inventoryRepository,
                              BranchRepository branchRepository,
                              ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
    }

    /**
     * Validates a create request.
     *
     * @param request inventory request
     */
    public void validateCreate(InventoryRequest request) {
        ensureBranchExists(request.getBranchId());
        ensureProductExists(request.getProductId());
        if (inventoryRepository.existsByBranchIdAndProductId(
                request.getBranchId(), request.getProductId())) {
            throw new BusinessException("Inventory record already exists for this branch and product");
        }
    }

    /**
     * Validates an update request.
     *
     * @param id      inventory identifier
     * @param request inventory request
     */
    public void validateUpdate(Long id, InventoryRequest request) {
        ensureBranchExists(request.getBranchId());
        ensureProductExists(request.getProductId());
        if (inventoryRepository.existsByBranchIdAndProductIdAndIdNot(
                request.getBranchId(), request.getProductId(), id)) {
            throw new BusinessException("Inventory record already exists for this branch and product");
        }
    }

    private void ensureBranchExists(Long branchId) {
        branchRepository.findByIdAndActiveTrue(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", "id", branchId));
    }

    private void ensureProductExists(Long productId) {
        productRepository.findByIdAndActiveTrue(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }
}
