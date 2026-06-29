package com.nextstack.airetail.product.repository;

import com.nextstack.airetail.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link Product} entities.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByIdAndActiveTrue(Long id);

    Optional<Product> findBySkuAndCompanyId(String sku, Long companyId);

    Optional<Product> findBySkuAndCompanyIdAndActiveTrue(String sku, Long companyId);

    boolean existsBySkuAndCompanyId(String sku, Long companyId);

    boolean existsBySkuAndCompanyIdAndIdNot(String sku, Long companyId, Long id);
}
