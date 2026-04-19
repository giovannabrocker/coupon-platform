package com.landor.coupon.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.landor.coupon.domain.entity.Coupon;
import com.landor.coupon.dto.CouponRequestDto;
import com.landor.coupon.dto.CouponResponseDto;
import com.landor.coupon.exception.BusinessException;
import com.landor.coupon.exception.ErrorCode;
import com.landor.coupon.repository.CouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository repository;

    public List<CouponResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CouponResponseDto create(CouponRequestDto request) {

        Coupon coupon = Coupon.create(
                request.code(),
                request.description(),
                request.discountValue(),
                request.expirationDate(),
                request.published()
        );

        Coupon saved = repository.save(coupon);

        return toResponse(saved);
    }

    public void delete(UUID id) {
        Coupon coupon = repository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorCode.COUPON_NOT_FOUND));

        coupon.delete();

        repository.save(coupon);
    }

    private CouponResponseDto toResponse(Coupon coupon) {
        return new CouponResponseDto(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDescription(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.getStatus(),
                coupon.getPublished(),
                coupon.getRedeemed()
        );
    }
}