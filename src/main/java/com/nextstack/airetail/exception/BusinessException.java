package com.nextstack.airetail.exception;

import org.springframework.http.HttpStatus;

/**
 * Thrown when a business rule is violated.
 */
public class BusinessException extends BaseException {

    /**
     * Creates a business exception with the given message.
     *
     * @param message error message
     */
    public BusinessException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * Creates a business exception with custom HTTP status.
     *
     * @param status  HTTP status
     * @param message error message
     */
    public BusinessException(HttpStatus status, String message) {
        super(status, message);
    }
}
