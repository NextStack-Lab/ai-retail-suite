package com.nextstack.airetail.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Standard API response wrapper for all REST endpoints.
 *
 * @param <T> the type of the response data payload
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private Instant timestamp;
    private String path;

    /**
     * Creates a successful response with data.
     *
     * @param message success message
     * @param data    response payload
     * @param path    request path
     * @param <T>     payload type
     * @return wrapped API response
     */
    public static <T> ApiResponse<T> success(String message, T data, String path) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .path(path)
                .build();
    }

    /**
     * Creates a successful response without data.
     *
     * @param message success message
     * @param path    request path
     * @return wrapped API response
     */
    public static ApiResponse<Void> success(String message, String path) {
        return success(message, null, path);
    }

    /**
     * Creates an error response.
     *
     * @param message error message
     * @param path    request path
     * @return wrapped API response
     */
    public static ApiResponse<Void> error(String message, String path) {
        return ApiResponse.<Void>builder()
                .success(false)
                .message(message)
                .timestamp(Instant.now())
                .path(path)
                .build();
    }
}
