package com.nextstack.airetail.security;

import com.nextstack.airetail.config.AppProperties;
import com.nextstack.airetail.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link JwtTokenProvider}.
 */
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        AppProperties appProperties = new AppProperties();
        AppProperties.Jwt jwt = new AppProperties.Jwt();
        jwt.setSecret("testSecretKeyForJwtTokenProviderTestsMustBe256Bits!!");
        jwt.setAccessTokenExpirationMs(3600000L);
        appProperties.setJwt(jwt);
        jwtTokenProvider = new JwtTokenProvider(appProperties);
    }

    @Test
    void generateAndValidateToken_shouldSucceed() {
        User user = User.builder()
                .username("testuser")
                .email("test@test.com")
                .password("pass")
                .roles(Set.of())
                .build();
        user.setId(1L);
        user.setActive(true);

        UserPrincipal principal = new UserPrincipal(user);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        String token = jwtTokenProvider.generateAccessToken(authentication);

        assertThat(token).isNotBlank();
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
        assertThat(jwtTokenProvider.getUsernameFromToken(token)).isEqualTo("testuser");
    }

    @Test
    void validateToken_shouldReturnFalseForInvalidToken() {
        assertThat(jwtTokenProvider.validateToken("invalid.token.here")).isFalse();
    }
}
