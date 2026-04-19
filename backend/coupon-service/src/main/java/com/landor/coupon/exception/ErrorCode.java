package com.landor.coupon.exception;

public enum ErrorCode {

    COUPON_ALREADY_DELETED("Coupon already deleted"),
    INVALID_COUPON_CODE("Coupon code must have 6 characters"),
    INVALID_DISCOUNT_VALUE("Discount must be at least 0.5"),
    INVALID_EXPIRATION_DATE("Expiration date cannot be in the past"),
    COUPON_NOT_FOUND("Coupon not found"),
    INTERNAL_ERROR("An unexpected error occurred");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}