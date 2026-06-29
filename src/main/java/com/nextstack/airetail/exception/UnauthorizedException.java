package com.nextstack.airetail.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when authentication fails or credentials are invalid.
 */
public class UnauthorizedException extends BaseException {

    /**
     * Creates an unauthorized exception with the given message.
     *
     * @param message error message
     */
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
