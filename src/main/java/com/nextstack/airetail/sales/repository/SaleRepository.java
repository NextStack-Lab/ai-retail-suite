package com.nextstack.airetail.sales.repository;

import com.nextstack.airetail.sales.entity.Sale;
import com.nextstack.airetail.sales.entity.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

/**
 * Repository for {@link Sale} entities.
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {

    Optional<Sale> findByIdAndActiveTrue(Long id);

    Optional<Sale> findBySaleNumberAndActiveTrue(String saleNumber);

    @Query("SELECT COALESCE(SUM(s.total), 0) FROM Sale s WHERE s.active = true "
            + "AND s.status = :status AND s.saleDate >= :startDate AND s.saleDate <= :endDate "
            + "AND (:branchId IS NULL OR s.branch.id = :branchId)")
    BigDecimal sumTotalByDateRangeAndBranch(@Param("startDate") Instant startDate,
                                            @Param("endDate") Instant endDate,
                                            @Param("branchId") Long branchId,
                                            @Param("status") SaleStatus status);

    @Query("SELECT COUNT(s) FROM Sale s WHERE s.active = true "
            + "AND s.status = :status AND s.saleDate >= :startDate AND s.saleDate <= :endDate "
            + "AND (:branchId IS NULL OR s.branch.id = :branchId)")
    long countByDateRangeAndBranch(@Param("startDate") Instant startDate,
                                   @Param("endDate") Instant endDate,
                                   @Param("branchId") Long branchId,
                                   @Param("status") SaleStatus status);
}
