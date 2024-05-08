package com.backend.supermeproject.cart.repository;

import com.backend.supermeproject.cart.entity.Cart;
import com.backend.supermeproject.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>, QuerydslPredicateExecutor<CartItem>, CartItemRepositoryCustom   {

    /**
     * 특정 사용자(Member)의 모든 카트 아이템을 조회합니다.
     *
     * @param memberId 조회할 멤버의 ID
     * @return 해당 멤버의 모든 카트 아이템 리스트
     */
    List<CartItem> findByCart_Member_MemberId(Long memberId);


    /**
     * 특정 아이템을 가지고 있는 사용자의 카트 아이템을 조회합니다.
     *
     * @param itemId   조회할 아이템 ID
     * @param memberId 조회할 멤버의 ID
     * @return 해당 멤버와 아이템 ID에 해당하는 카트 아이템
     */
    Optional<CartItem> findByIdAndCart_Member_MemberId(Long itemId, Long memberId);

    List<CartItem> findByCart_Member_MemberIdAndCart_IsPaidFalse(Long memberId);
    Optional<CartItem> findByMemberIdAndItemIdAndVariantIdAndSizeId(Long memberId, Long itemId, Long variantId, Long sizeId);


}
