package com.backend.supermeproject.member.service;

import com.backend.supermeproject.member.dto.ResponseLogin;
import com.backend.supermeproject.member.jwt.TokenProvider;
import com.backend.supermeproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLoginService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository repository;

    public ResponseLogin authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //인증 정보를 가지고 JWT AccessToken 발급
        String accessToken = tokenProvider.createToken(authentication);
        return ResponseLogin.builder()
                .accessToken(accessToken)
                .build();
    }
}
