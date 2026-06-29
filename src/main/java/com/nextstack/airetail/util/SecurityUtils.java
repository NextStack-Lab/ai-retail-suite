package com.nextstack.airetail.util;

import com.nextstack.airetail.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security context utility methods.
 */
public final class SecurityUtils {

    private SecurityUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Returns the currently authenticated user's principal.
     *
     * @return user principal or null if not authenticated
     */
    public static UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal principal) {
            return principal;
        }
        return null;
    }

    /**
     * Returns the current user's username or "system" if anonymous.
     *
     * @return username
     */
    public static String getCurrentUsername() {
        UserPrincipal user = getCurrentUser();
        return user != null ? user.getUsername() : "system";
    }

    /**
     * Returns the current user's company ID.
     *
     * @return company ID or null
     */
    public static Long getCurrentCompanyId() {
        UserPrincipal user = getCurrentUser();
        return user != null ? user.getCompanyId() : null;
    }
}
