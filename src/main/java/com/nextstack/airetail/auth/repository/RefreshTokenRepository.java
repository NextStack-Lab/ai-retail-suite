package com.nextstack.airetail.auth.repository;

import com.nextstack.airetail.auth.entity.RefreshToken;
import com.nextstack.airetail.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link RefreshToken} entities.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("SELECT rt FROM RefreshToken rt "
            + "JOIN FETCH rt.user u "
            + "LEFT JOIN FETCH u.roles r "
            + "LEFT JOIN FETCH r.permissions "
            + "WHERE rt.token = :token")
    Optional<RefreshToken> findByTokenWithUser(@Param("token") String token);

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

    void deleteByToken(String token);
}
