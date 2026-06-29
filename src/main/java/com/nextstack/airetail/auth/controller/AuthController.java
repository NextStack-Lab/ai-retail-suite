package com.nextstack.airetail.auth.controller;

import com.nextstack.airetail.auth.dto.request.ForgotPasswordRequest;
import com.nextstack.airetail.auth.dto.request.LoginRequest;
import com.nextstack.airetail.auth.dto.request.RefreshTokenRequest;
import com.nextstack.airetail.auth.dto.request.ResetPasswordRequest;
import com.nextstack.airetail.auth.dto.response.AuthResponse;
import com.nextstack.airetail.auth.service.AuthService;
import com.nextstack.airetail.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentication operations.
 */
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Creates the controller with auth service dependency.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticates a user and returns JWT tokens.
     *
     * @param request     login credentials
     * @param httpRequest HTTP request for path logging
     * @return authentication response
     */
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request,
                                           HttpServletRequest httpRequest) {
        return ApiResponse.success("Login successful",
                authService.login(request), httpRequest.getRequestURI());
    }

    /**
     * Invalidates the user's refresh token.
     *
     * @param request     refresh token request
     * @param httpRequest HTTP request for path logging
     * @return success response
     */
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@Valid @RequestBody RefreshTokenRequest request,
                                    HttpServletRequest httpRequest) {
        authService.logout(request);
        return ApiResponse.success("Logout successful", httpRequest.getRequestURI());
    }

    /**
     * Refreshes the access token.
     *
     * @param request     refresh token request
     * @param httpRequest HTTP request for path logging
     * @return new authentication response
     */
    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request,
                                               HttpServletRequest httpRequest) {
        return ApiResponse.success("Token refreshed successfully",
                authService.refresh(request), httpRequest.getRequestURI());
    }

    /**
     * Initiates forgot-password flow.
     *
     * @param request     forgot password request
     * @param httpRequest HTTP request for path logging
     * @return success response
     */
    @PostMapping("/forgot-password")
    public ApiResponse<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request,
                                            HttpServletRequest httpRequest) {
        authService.forgotPassword(request);
        return ApiResponse.success("If the email exists, a reset link has been sent", httpRequest.getRequestURI());
    }

    /**
     * Resets the user's password.
     *
     * @param request     reset password request
     * @param httpRequest HTTP request for path logging
     * @return success response
     */
    @PostMapping("/reset-password")
    public ApiResponse<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request,
                                           HttpServletRequest httpRequest) {
        authService.resetPassword(request);
        return ApiResponse.success("Password reset successfully", httpRequest.getRequestURI());
    }
}
