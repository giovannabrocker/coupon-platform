package com.landor.coupon.exception;

import java.time.OffsetDateTime;

public class ErrorResponse {

    private final String code;
    private final String message;
    private final OffsetDateTime timestamp;

    public ErrorResponse(ErrorCode errorCode, OffsetDateTime timestamp) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
        this.timestamp = timestamp;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public OffsetDateTime getTimestamp() { return timestamp; }
}