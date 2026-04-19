package com.landor.coupon.controller;

import com.landor.coupon.domain.entity.Coupon;
import com.landor.coupon.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CouponControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CouponRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void findAll_endpointRespondsSuccessfully() throws Exception {
        Coupon coupon = Coupon.create("ABC123", "desc", 1.0, OffsetDateTime.now().plusDays(1), true);
        repository.save(coupon);

        mockMvc.perform(get("/coupon"))
                .andExpect(status().isOk());
    }
}
