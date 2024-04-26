package com.backend.supermeproject.member.dto;

import lombok.Builder;

@Builder
public record ResponseLogin(
        String accessToken
) {
}
