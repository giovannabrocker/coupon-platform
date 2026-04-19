package com.landor.coupon.domain;

import com.landor.coupon.domain.entity.Coupon;
import com.landor.coupon.domain.enums.CouponStatus;
import com.landor.coupon.exception.BusinessException;
import com.landor.coupon.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponTest {

    @Test
    void shouldSanitizeCodeOnCreation() {
        Coupon coupon = Coupon.create(" aB 12- cd ", "Desc", 10.0, futureDate(), true);

        assertThat(coupon.getCode()).isEqualTo("AB12CD");
    }

    @Test
    void shouldDefaultPublishedToFalseWhenNotProvided() {
        Coupon coupon = Coupon.create("ABC123", "desc", 5.0, futureDate(), null);

        assertThat(coupon.getPublished()).isFalse();
    }

    @Test
    void shouldApplyDefaultStatusAndRedeemedFlagsOnCreation() {
        Coupon coupon = Coupon.create("ABC123", "desc", 5.0, futureDate(), true);

        assertThat(coupon.getStatus()).isEqualTo(CouponStatus.ACTIVE);
        assertThat(coupon.getRedeemed()).isFalse();
        assertThat(coupon.isDeleted()).isFalse();
    }

    @Test
    void shouldRejectPastExpirationDates() {
        OffsetDateTime past = OffsetDateTime.now().minusDays(1);

        assertThatThrownBy(() -> Coupon.create("ABC123", "Desc", 10.0, past, true))
                .isInstanceOf(BusinessException.class)
                .matches(ex -> ((BusinessException) ex).getCode() == ErrorCode.INVALID_EXPIRATION_DATE);
    }

    @Test
    void shouldRejectDiscountBelowMinimum() {
        assertThatThrownBy(() -> Coupon.create("ABC123", "Desc", 0.49, futureDate(), true))
                .isInstanceOf(BusinessException.class)
                .matches(ex -> ((BusinessException) ex).getCode() == ErrorCode.INVALID_DISCOUNT_VALUE);
    }

    @Test
    void shouldRejectInvalidCodes() {
        assertThatThrownBy(() -> Coupon.create("aa-b", "Desc", 5.0, futureDate(), true))
                .isInstanceOf(BusinessException.class)
                .matches(ex -> ((BusinessException) ex).getCode() == ErrorCode.INVALID_COUPON_CODE);
    }

    @Test
    void shouldMarkCouponAsDeleted() {
        Coupon coupon = Coupon.create("ABC123", "Desc", 5.0, futureDate(), true);

        coupon.delete();

        assertThat(coupon.isDeleted()).isTrue();
        assertThat(coupon.getStatus()).isEqualTo(CouponStatus.INACTIVE);
        assertThat(coupon.getDeletedAt()).isNotNull();
    }

    @Test
    void shouldNotDeleteTwice() {
        Coupon coupon = Coupon.create("ABC123", "Desc", 5.0, futureDate(), true);
        coupon.delete();

        assertThatThrownBy(coupon::delete)
                .isInstanceOf(BusinessException.class)
                .matches(ex -> ((BusinessException) ex).getCode() == ErrorCode.COUPON_ALREADY_DELETED);
    }

    private OffsetDateTime futureDate() {
        return OffsetDateTime.now().plusDays(1);
    }
}
