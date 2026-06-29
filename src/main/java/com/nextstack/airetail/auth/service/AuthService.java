package com.nextstack.airetail.auth.service;

import com.nextstack.airetail.auth.dto.request.ForgotPasswordRequest;
import com.nextstack.airetail.auth.dto.request.LoginRequest;
import com.nextstack.airetail.auth.dto.request.RefreshTokenRequest;
import com.nextstack.airetail.auth.dto.request.ResetPasswordRequest;
import com.nextstack.airetail.auth.dto.response.AuthResponse;

/**
 * Service contract for authentication operations.
 */
public interface AuthService {

    /**
     * Authenticates a user and returns JWT tokens.
     *
     * @param request login credentials
     * @return authentication response with tokens
     */
    AuthResponse login(LoginRequest request);

    /**
     * Invalidates the user's refresh token (logout).
     *
     * @param request refresh token request
     */
    void logout(RefreshTokenRequest request);

    /**
     * Refreshes the access token using a valid refresh token.
     *
     * @param request refresh token request
     * @return new authentication response
     */
    AuthResponse refresh(RefreshTokenRequest request);

    /**
     * Initiates forgot-password flow by sending a reset email.
     *
     * @param request forgot password request
     */
    void forgotPassword(ForgotPasswordRequest request);

    /**
     * Resets the user's password using a valid reset token.
     *
     * @param request reset password request
     */
    void resetPassword(ResetPasswordRequest request);
}
