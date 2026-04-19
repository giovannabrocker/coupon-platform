package com.landor.coupon.service;

import com.landor.coupon.domain.entity.Coupon;
import com.landor.coupon.domain.enums.CouponStatus;
import com.landor.coupon.dto.CouponRequestDto;
import com.landor.coupon.dto.CouponResponseDto;
import com.landor.coupon.exception.BusinessException;
import com.landor.coupon.exception.ErrorCode;
import com.landor.coupon.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CouponService.class, CouponServiceTest.MockRepositoryConfig.class})
class CouponServiceTest {

    @Autowired
    private CouponService service;

    @MockBean
    @Autowired
    private CouponRepository repository;

    @Test
    void findAll_returnsMappedResponses() {
        Coupon coupon = Coupon.create("ABC123", "description", 3.0, futureDate(), true);
        when(repository.findAll()).thenReturn(List.of(coupon));

        List<CouponResponseDto> responses = service.findAll();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).code()).isEqualTo("ABC123");
        assertThat(responses.get(0).status()).isEqualTo(CouponStatus.ACTIVE);
    }

    @Test
    void create_savesSanitizedCoupon() {
        CouponRequestDto request = new CouponRequestDto("ab c-12?3", "description", 12.0, futureDate(), null);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CouponResponseDto response = service.create(request);

        ArgumentCaptor<Coupon> couponCaptor = ArgumentCaptor.forClass(Coupon.class);
        verify(repository).save(couponCaptor.capture());
        Coupon captured = couponCaptor.getValue();

        assertThat(captured.getCode()).isEqualTo("ABC123");
        assertThat(response.code()).isEqualTo("ABC123");
        assertThat(response.published()).isFalse();
    }

    @Test
    void delete_existingCoupon_marksAsDeleted() {
        UUID id = UUID.randomUUID();
        Coupon coupon = Coupon.create("ABC123", "description", 5.0, futureDate(), true);
        when(repository.findById(id)).thenReturn(Optional.of(coupon));

        service.delete(id);

        verify(repository).save(coupon);
        assertThat(coupon.isDeleted()).isTrue();
        assertThat(coupon.getStatus()).isEqualTo(CouponStatus.INACTIVE);
        assertThat(coupon.getDeletedAt()).isNotNull();
    }

    @Test
    void delete_missingCoupon_throws() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(BusinessException.class)
                .matches(ex -> ((BusinessException) ex).getCode() == ErrorCode.COUPON_NOT_FOUND);
    }

    private OffsetDateTime futureDate() {
        return OffsetDateTime.now().plusDays(5);
    }

    @TestConfiguration
    static class MockRepositoryConfig {

        @Bean
        CouponRepository couponRepository() {
            return Mockito.mock(CouponRepository.class);
        }
    }
}
