package com.backend.supermeproject.member.service;

import com.backend.supermeproject.global.exception.BusinessException;
import com.backend.supermeproject.global.exception.ErrorCode;
import com.backend.supermeproject.global.role.MemberType;
import com.backend.supermeproject.member.entity.Member;
import com.backend.supermeproject.member.jwt.MemberInfo;
import com.backend.supermeproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
        MemberType type = Objects.requireNonNullElse(member.getType(), MemberType.USER);

        // 권한 객체 생성
        List<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(type.name()));

        return MemberInfo.builder()
                .email(member.getEmail())
                .member(member)
                .authorities(authorities)
                .build();
    }
}
