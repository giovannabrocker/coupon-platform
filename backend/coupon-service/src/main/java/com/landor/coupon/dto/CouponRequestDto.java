package com.landor.coupon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.time.OffsetDateTime;

public record CouponRequestDto(

    @NotBlank
    String code,

    @NotBlank
    String description,

    @NotNull
    @DecimalMin(value = "0.5", inclusive = true)
    Double discountValue,

    @NotNull
    OffsetDateTime expirationDate,

    Boolean published

) {}