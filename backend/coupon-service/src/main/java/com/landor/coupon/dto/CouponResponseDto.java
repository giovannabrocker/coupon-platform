package com.landor.coupon.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.landor.coupon.domain.enums.CouponStatus;

public record CouponResponseDto(
    UUID id,
    String code,
    String description,
    Double discountValue,
    OffsetDateTime expirationDate,
    CouponStatus status,
    Boolean published,
    Boolean redeemed
) {}