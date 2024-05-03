package com.backend.supermeproject.member.repository;

import com.backend.supermeproject.member.entity.Member;
import com.backend.supermeproject.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Member> findByEmail(String email) {
        QMember qMember = QMember.member;

        Member member = queryFactory.selectFrom(qMember)
                .where(qMember.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(member);
    }
}
