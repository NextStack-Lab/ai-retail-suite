package com.nextstack.airetail.role.repository;

import com.nextstack.airetail.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link Role} entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    Optional<Role> findByIdAndActiveTrue(Long id);

    Optional<Role> findByCodeAndCompanyIdAndActiveTrue(String code, Long companyId);

    boolean existsByCodeAndCompanyId(String code, Long companyId);

    boolean existsByCodeAndCompanyIdAndIdNot(String code, Long companyId, Long id);
}
