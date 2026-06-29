package com.nextstack.airetail.inventory.repository;

import com.nextstack.airetail.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Inventory} entities.
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {

    Optional<Inventory> findByIdAndActiveTrue(Long id);

    Optional<Inventory> findByBranchIdAndProductIdAndActiveTrue(Long branchId, Long productId);

    boolean existsByBranchIdAndProductId(Long branchId, Long productId);

    boolean existsByBranchIdAndProductIdAndIdNot(Long branchId, Long productId, Long id);

    List<Inventory> findByBranchIdAndActiveTrue(Long branchId);
}
