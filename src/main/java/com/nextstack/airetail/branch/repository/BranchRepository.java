package com.nextstack.airetail.branch.repository;

import com.nextstack.airetail.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link Branch} entities.
 */
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long>, JpaSpecificationExecutor<Branch> {

    Optional<Branch> findByIdAndActiveTrue(Long id);

    Optional<Branch> findByCodeAndCompanyIdAndActiveTrue(String code, Long companyId);

    boolean existsByCodeAndCompanyId(String code, Long companyId);

    boolean existsByCodeAndCompanyIdAndIdNot(String code, Long companyId, Long id);
}
