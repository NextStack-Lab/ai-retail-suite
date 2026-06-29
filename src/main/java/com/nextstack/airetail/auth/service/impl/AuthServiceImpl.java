package com.nextstack.airetail.auth.service.impl;

import com.nextstack.airetail.auth.dto.request.ForgotPasswordRequest;
import com.nextstack.airetail.auth.dto.request.LoginRequest;
import com.nextstack.airetail.auth.dto.request.RefreshTokenRequest;
import com.nextstack.airetail.auth.dto.request.ResetPasswordRequest;
import com.nextstack.airetail.auth.dto.response.AuthResponse;
import com.nextstack.airetail.auth.entity.PasswordResetToken;
import com.nextstack.airetail.auth.entity.RefreshToken;
import com.nextstack.airetail.auth.repository.PasswordResetTokenRepository;
import com.nextstack.airetail.auth.repository.RefreshTokenRepository;
import com.nextstack.airetail.auth.service.AuthService;
import com.nextstack.airetail.config.AppProperties;
import com.nextstack.airetail.exception.BusinessException;
import com.nextstack.airetail.exception.UnauthorizedException;
import com.nextstack.airetail.security.JwtTokenProvider;
import com.nextstack.airetail.security.UserPrincipal;
import com.nextstack.airetail.user.entity.User;
import com.nextstack.airetail.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * Implementation of {@link AuthService}.
 */
@Slf4j
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private static final String TOKEN_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppProperties appProperties;
    private final JavaMailSender mailSender;

    /**
     * Creates the service with required dependencies.
     */
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider,
                           RefreshTokenRepository refreshTokenRepository,
                           PasswordResetTokenRepository passwordResetTokenRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AppProperties appProperties,
                           JavaMailSender mailSender) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appProperties = appProperties;
        this.mailSender = mailSender;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findByIdAndActiveTrue(principal.getId())
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        user.setLastLoginAt(Instant.now());
        userRepository.save(user);

        refreshTokenRepository.deleteByUser(user);
        RefreshToken refreshToken = createRefreshToken(user);

        return buildAuthResponse(accessToken, refreshToken.getToken());
    }

    @Override
    public void logout(RefreshTokenRequest request) {
        refreshTokenRepository.deleteByToken(request.getRefreshToken());
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByTokenWithUser(request.getRefreshToken())
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new UnauthorizedException("Refresh token has expired");
        }

        User user = refreshToken.getUser();
        UserPrincipal principal = new UserPrincipal(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, null, principal.getAuthorities());
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);

        return buildAuthResponse(accessToken, refreshToken.getToken());
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        userRepository.findByEmailAndActiveTrue(request.getEmail()).ifPresent(user -> {
            passwordResetTokenRepository.deleteByUser(user);
            String token = UUID.randomUUID().toString();
            PasswordResetToken resetToken = PasswordResetToken.builder()
                    .token(token)
                    .expiryDate(Instant.now().plusMillis(appProperties.getJwt().getAccessTokenExpirationMs() * 4))
                    .used(false)
                    .user(user)
                    .build();
            resetToken.setActive(true);
            passwordResetTokenRepository.save(resetToken);
            sendResetEmail(user.getEmail(), token);
        });
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new BusinessException("Invalid or expired reset token"));

        if (Boolean.TRUE.equals(resetToken.getUsed())) {
            throw new BusinessException("Reset token has already been used");
        }
        if (resetToken.getExpiryDate().isBefore(Instant.now())) {
            throw new BusinessException("Reset token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(appProperties.getJwt().getRefreshTokenExpirationMs()))
                .user(user)
                .build();
        refreshToken.setActive(true);
        return refreshTokenRepository.save(refreshToken);
    }

    private AuthResponse buildAuthResponse(String accessToken, String refreshToken) {
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(TOKEN_TYPE)
                .expiresIn(jwtTokenProvider.getAccessTokenExpirationMs())
                .build();
    }

    private void sendResetEmail(String email, String token) {
        try {
            String resetUrl = appProperties.getMail().getResetPasswordUrl() + "?token=" + token;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(appProperties.getMail().getFrom());
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("Click the link to reset your password: " + resetUrl);
            mailSender.send(message);
        } catch (Exception ex) {
            log.warn("Failed to send password reset email to {}: {}", email, ex.getMessage());
        }
    }
}
