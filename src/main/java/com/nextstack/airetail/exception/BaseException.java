package com.nextstack.airetail.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base runtime exception for application-specific errors.
 */
@Getter
public class BaseException extends RuntimeException {

    private final HttpStatus status;

    /**
     * Creates an exception with HTTP status and message.
     *
     * @param status  HTTP status to return
     * @param message human-readable error message
     */
    public BaseException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
