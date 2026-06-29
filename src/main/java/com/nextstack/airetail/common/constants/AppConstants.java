package com.nextstack.airetail.common.constants;

/**
 * Application-wide constants.
 */
public final class AppConstants {

    public static final String API_VERSION = "v1";
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "20";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    public static final int MAX_PAGE_SIZE = 100;

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    public static final String ROLE_COMPANY_ADMIN = "ROLE_COMPANY_ADMIN";
    public static final String ROLE_BRANCH_MANAGER = "ROLE_BRANCH_MANAGER";
    public static final String ROLE_CASHIER = "ROLE_CASHIER";
    public static final String ROLE_INVENTORY_MANAGER = "ROLE_INVENTORY_MANAGER";

    private AppConstants() {
        throw new UnsupportedOperationException("Utility class");
    }
}
