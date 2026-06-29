package com.nextstack.airetail.brand.repository;

import com.nextstack.airetail.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link Brand} entities.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

    Optional<Brand> findByIdAndActiveTrue(Long id);

    boolean existsByNameAndCompanyId(String name, Long companyId);

    boolean existsByNameAndCompanyIdAndIdNot(String name, Long companyId, Long id);
}
