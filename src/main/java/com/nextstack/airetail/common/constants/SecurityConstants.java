package com.nextstack.airetail.common.constants;

/**
 * Security-related constants.
 */
public final class SecurityConstants {

    public static final String[] PUBLIC_URLS = {
            "/v1/auth/login",
            "/v1/auth/refresh",
            "/v1/auth/forgot-password",
            "/v1/auth/reset-password",
            "/actuator/health",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    private SecurityConstants() {
        throw new UnsupportedOperationException("Utility class");
    }
}
