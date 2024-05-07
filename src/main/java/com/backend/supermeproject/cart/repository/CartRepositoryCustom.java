package com.backend.supermeproject.cart.repository;

import com.backend.supermeproject.cart.entity.Cart;

import java.util.Optional;

public interface CartRepositoryCustom {
    /**
     * 사용자의 ID로 가장 최근의 활성화된 카트를 찾습니다.
     * 활성화된 카트는 결제되지 않은 카트를 의미합니다.
     *
     * @param memberId 사용자 ID
     * @return 가장 최근의 활성화된 카트
     */
    Optional<Cart> findLatestActiveCartByMemberId(Long memberId);
}
