package com.mall.shopnest.api;

public enum ResultCode implements IErrorCode {

    SUCCESS(200, "Operation successful"),
    FAILED(500, "Operation failed"),
    VALIDATE_FAILED(404, "Parameter validation failed"),
    UNAUTHORIZED(401, "Not logged in or token has expired"),
    FORBIDDEN(403, "No relevant permissions");

    private final long code;
    private final String msg;

    ResultCode(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
