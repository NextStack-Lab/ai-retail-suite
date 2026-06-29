package com.nextstack.airetail.auth;

import com.nextstack.airetail.auth.dto.request.LoginRequest;
import com.nextstack.airetail.auth.dto.response.AuthResponse;
import com.nextstack.airetail.auth.entity.RefreshToken;
import com.nextstack.airetail.auth.repository.PasswordResetTokenRepository;
import com.nextstack.airetail.auth.repository.RefreshTokenRepository;
import com.nextstack.airetail.auth.service.impl.AuthServiceImpl;
import com.nextstack.airetail.config.AppProperties;
import com.nextstack.airetail.security.JwtTokenProvider;
import com.nextstack.airetail.security.UserPrincipal;
import com.nextstack.airetail.user.entity.User;
import com.nextstack.airetail.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link AuthServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JavaMailSender mailSender;

    private AppProperties appProperties;
    private JwtTokenProvider jwtTokenProvider;
    private AuthServiceImpl authService;

    private User user;
    private UserPrincipal principal;

    @BeforeEach
    void setUp() {
        appProperties = new AppProperties();
        AppProperties.Jwt jwt = new AppProperties.Jwt();
        jwt.setSecret("testSecretKeyForAuthServiceTestsMustBeAtLeast256BitsLong!!");
        jwt.setAccessTokenExpirationMs(900000L);
        jwt.setRefreshTokenExpirationMs(604800000L);
        appProperties.setJwt(jwt);

        jwtTokenProvider = new JwtTokenProvider(appProperties);
        authService = new AuthServiceImpl(
                authenticationManager,
                jwtTokenProvider,
                refreshTokenRepository,
                passwordResetTokenRepository,
                userRepository,
                passwordEncoder,
                appProperties,
                mailSender);

        user = User.builder()
                .username("admin")
                .email("admin@test.com")
                .password("encoded")
                .roles(Set.of())
                .build();
        user.setId(1L);
        user.setActive(true);
        principal = new UserPrincipal(user);
    }

    @Test
    void login_shouldReturnTokens() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, null, principal.getAuthorities());

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(userRepository.findByIdAndActiveTrue(1L)).thenReturn(Optional.of(user));
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenAnswer(inv -> inv.getArgument(0));

        AuthResponse response = authService.login(request);

        assertThat(response.getAccessToken()).isNotBlank();
        assertThat(response.getTokenType()).isEqualTo("Bearer");
        assertThat(response.getRefreshToken()).isNotNull();
        verify(refreshTokenRepository).deleteByUser(user);
    }
}
