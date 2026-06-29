package com.nextstack.airetail.permission.repository;

import com.nextstack.airetail.permission.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link Permission} entities.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    Optional<Permission> findByIdAndActiveTrue(Long id);

    Optional<Permission> findByCodeAndActiveTrue(String code);
}
