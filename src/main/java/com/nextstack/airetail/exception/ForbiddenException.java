package com.nextstack.airetail.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when the authenticated user lacks required permissions.
 */
public class ForbiddenException extends BaseException {

    /**
     * Creates a forbidden exception with the given message.
     *
     * @param message error message
     */
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
