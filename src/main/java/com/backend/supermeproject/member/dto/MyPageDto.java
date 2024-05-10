package com.backend.supermeproject.member.dto;

import com.backend.supermeproject.global.role.Gender;

import java.math.BigDecimal;

public record MyPageDto(
        String profileImage,
        String name,
        String email,
        String phoneNumber,
        String country,
        String address,
        String city,
        String postcode,
        Gender gender,
        BigDecimal deposit
        ) {


}
