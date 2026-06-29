package com.nextstack.airetail.customer.repository;

import com.nextstack.airetail.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link Customer} entities.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByIdAndActiveTrue(Long id);

    Optional<Customer> findByEmailAndCompanyIdAndActiveTrue(String email, Long companyId);
}
