package com.landor.coupon.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.landor.coupon.domain.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {

}