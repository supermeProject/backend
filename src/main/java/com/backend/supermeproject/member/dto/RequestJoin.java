package com.backend.supermeproject.member.dto;

import com.backend.supermeproject.global.role.Gender;

public record RequestJoin(
        String email,
        String password,
        String nickname,
        String address,
        String phoneNumber,
        Gender gender


) {
}
