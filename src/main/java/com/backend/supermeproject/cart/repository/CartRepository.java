package com.backend.supermeproject.cart.repository;

import com.backend.supermeproject.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, QuerydslPredicateExecutor<Cart> {
    // 특정 회원의 ID에 따라 카트를 찾는 메소드
    Optional<Cart> findByMember_MemberId(Long memberId);
}
