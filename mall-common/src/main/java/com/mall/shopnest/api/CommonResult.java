package com.mall.shopnest.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * General response wrapper
 * @param <T> response data type
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {

    private long code;
    private String message;
    private T data;

    /**
     * Default success response
     * @param data response data
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * Success response with custom message
     * @param data response data
     * @param message custom message
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * Response based on custom error code
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * Failure response with custom message
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * General failure response
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * Parameter validation failed (default message)
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * Parameter validation failed with custom message
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * Unauthorized response (user not logged in)
     */
    public static <T> CommonResult<T> unauthorized() {
        return failed(ResultCode.UNAUTHORIZED);
    }

    /**
     * Unauthorized response with custom message
     */
    public static <T> CommonResult<T> unauthorized(String message) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), message, null);
    }

    /**
     * Forbidden response (user lacks permission)
     */
    public static <T> CommonResult<T> forbidden(String message) {
        return failed(ResultCode.FORBIDDEN);
    }
}
