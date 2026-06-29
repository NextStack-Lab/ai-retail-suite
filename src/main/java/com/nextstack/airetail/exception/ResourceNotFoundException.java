package com.nextstack.airetail.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends BaseException {

    /**
     * Creates a not-found exception for the given resource.
     *
     * @param resource resource name (e.g. "Company")
     * @param field    field used for lookup
     * @param value    lookup value
     */
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(HttpStatus.NOT_FOUND,
                String.format("%s not found with %s: '%s'", resource, field, value));
    }

    /**
     * Creates a not-found exception with a custom message.
     *
     * @param message error message
     */
    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
