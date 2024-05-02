package com.backend.supermeproject.member.dto;

import com.backend.supermeproject.global.role.Gender;

public record RequestJoin(
        String profileImage,
        String name,
        String email,
        String password,
        String passwordCheck,
        String phoneNumber,
        String country,
        String address,
        String city,
        String postcode,
        String gender
) {
}
