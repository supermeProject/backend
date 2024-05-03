package com.backend.supermeproject.member.repository;

import com.backend.supermeproject.member.entity.Member;

import java.util.Optional;

public interface MemberCustomRepository  {
    Optional<Member> findByEmail(String email);
}
