package com.landor.coupon.domain.entity;

import com.landor.coupon.exception.BusinessException;
import com.landor.coupon.exception.ErrorCode;
import com.landor.coupon.domain.enums.CouponStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupons")
@SQLDelete(sql = "UPDATE coupons SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String code;
    private String description;
    private Double discountValue;

    private OffsetDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    private Boolean published;
    private Boolean redeemed;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    private OffsetDateTime deletedAt;

    private Coupon(
            String code,
            String description,
            Double discountValue,
            OffsetDateTime expirationDate,
            Boolean published
    ) {
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.published = published;
        this.status = CouponStatus.ACTIVE;
        this.redeemed = false;
        this.deleted = false;
    }

    public static Coupon create(
            String code,
            String description,
            Double discountValue,
            OffsetDateTime expirationDate,
            Boolean published
    ) {
        String sanitizedCode = sanitizeCode(code);

        validateCode(sanitizedCode);
        validateDiscountValue(discountValue);
        validateExpirationDate(expirationDate);

        return new Coupon(
                sanitizedCode,
                description,
                discountValue,
                expirationDate,
                published != null ? published : false
        );
    }

    public void delete() {
        if (this.deleted) {
            throw new BusinessException(ErrorCode.COUPON_ALREADY_DELETED);
        }

        this.deleted = true;
        this.deletedAt = OffsetDateTime.now();
        this.status = CouponStatus.INACTIVE;
    }

    private static void validateCode(String code) {
        if (code == null || code.length() != 6) {
            throw new BusinessException(ErrorCode.INVALID_COUPON_CODE);
        }
    }

    private static void validateDiscountValue(Double value) {
        if (value == null || value < 0.5) {
            throw new BusinessException(ErrorCode.INVALID_DISCOUNT_VALUE);
        }
    }

    private static void validateExpirationDate(OffsetDateTime date) {
        if (date == null || date.isBefore(OffsetDateTime.now())) {
            throw new BusinessException(ErrorCode.INVALID_EXPIRATION_DATE);
        }
    }

    private static String sanitizeCode(String code) {
        if (code == null) return null;

        return code.replaceAll("[^a-zA-Z0-9]", "")
                   .toUpperCase();
    }
}