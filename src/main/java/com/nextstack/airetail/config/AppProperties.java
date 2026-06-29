package com.nextstack.airetail.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Application-level configuration properties.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Jwt jwt = new Jwt();
    private Cors cors = new Cors();
    private Mail mail = new Mail();

    /**
     * JWT token configuration.
     */
    @Getter
    @Setter
    public static class Jwt {
        private String secret;
        private long accessTokenExpirationMs;
        private long refreshTokenExpirationMs;
    }

    /**
     * CORS configuration.
     */
    @Getter
    @Setter
    public static class Cors {
        private List<String> allowedOrigins;
        private List<String> allowedMethods;
        private List<String> allowedHeaders;
        private boolean allowCredentials;
    }

    /**
     * Mail configuration for password reset emails.
     */
    @Getter
    @Setter
    public static class Mail {
        private String from;
        private String resetPasswordUrl;
    }
}
