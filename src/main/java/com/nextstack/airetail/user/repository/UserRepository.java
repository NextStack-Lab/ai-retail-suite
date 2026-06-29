package com.nextstack.airetail.user.repository;

import com.nextstack.airetail.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link User} entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT DISTINCT u FROM User u "
            + "LEFT JOIN FETCH u.roles r "
            + "LEFT JOIN FETCH r.permissions "
            + "WHERE u.username = :username AND u.active = true")
    Optional<User> findByUsernameWithRolesAndPermissions(@Param("username") String username);

    Optional<User> findByIdAndActiveTrue(Long id);

    Optional<User> findByUsernameAndActiveTrue(String username);

    Optional<User> findByEmailAndActiveTrue(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameAndIdNot(String username, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);
}
