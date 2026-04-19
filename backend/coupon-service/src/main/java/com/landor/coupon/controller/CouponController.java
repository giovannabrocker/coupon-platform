package com.landor.coupon.controller;

import com.landor.coupon.dto.CouponRequestDto;
import com.landor.coupon.dto.CouponResponseDto;
import com.landor.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "Listar todos os cupons")
    @GetMapping
    public ResponseEntity<List<CouponResponseDto>> findAll() {
        List<CouponResponseDto> coupons = couponService.findAll();
        return ResponseEntity.ok(coupons);
    }

    @Operation(summary = "Criar um novo cupom")
    @PostMapping
    public ResponseEntity<CouponResponseDto> create(@RequestBody @Valid CouponRequestDto request) {
        CouponResponseDto response = couponService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Deletar um cupom")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        couponService.delete(id);
        return ResponseEntity.noContent().build();
    }
}