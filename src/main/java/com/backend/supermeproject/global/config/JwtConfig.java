package com.backend.supermeproject.global.config;

import com.backend.supermeproject.member.dto.jwt.JwtProperties;
import com.backend.supermeproject.member.jwt.TokenProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {
    @Bean
    public TokenProvider tokenProvider(JwtProperties jwtProperties) {
        return new TokenProvider(jwtProperties.getSecret(), jwtProperties.getAccessTokenValidityInseconds());
    }
}