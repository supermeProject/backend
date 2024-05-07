package com.backend.supermeproject.cart.repository;

import com.backend.supermeproject.cart.entity.CartItem;
import com.backend.supermeproject.item.entity.Item;

import java.util.List;
import java.util.Optional;

public interface CartItemRepositoryCustom {
    /**
     * 사용자 ID에 해당하는 모든 카트 아이템 조회
     *
     * @param memberId 조회할 사용자 ID
     * @return 해당 사용자 ID에 속한 CartItem 엔티티 리스트
     */
    List<CartItem> findCartItemsByMemberId(Long memberId);

    /**
     * 사용자 ID와 아이템 정보를 기반으로 새로운 카트 아이템을 추가
     *
     * @param memberId   사용자 ID
     * @param item     추가할 아이템 엔티티
     * @param quantity 추가할 수량
     * @return 새로 생성된 CartItem 엔티티
     */
    CartItem addCartItem(Long memberId, Item item, int quantity);



    /**
     * 특정 아이템 ID에 해당하는 카트 아이템을 삭제
     *
     * @param itemId 삭제할 카트 아이템 ID
     */
    void deleteCartItemById(Long itemId);

    /**
     * 특정 사용자 ID에 해당하는 모든 카트 아이템을 삭제합니다.
     *
     * @param memberId 삭제할 사용자 ID
     */
    void clearCartByMemberId(Long memberId);

    Optional<CartItem> findCartItemByMemberId(Long memberId);
}
