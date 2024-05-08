package com.backend.supermeproject.cart.repository;

import com.backend.supermeproject.cart.entity.Cart;
import com.backend.supermeproject.cart.entity.CartItem;
import com.backend.supermeproject.cart.entity.QCart;
import com.backend.supermeproject.cart.entity.QCartItem;
import com.backend.supermeproject.image.ImageEntity.QItemImage;
import com.backend.supermeproject.item.entity.Item;
import com.backend.supermeproject.item.entity.QItem;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CartItemRepositoryImpl implements CartItemRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public CartItemRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.entityManager = entityManager;
    }

    // 사용자 ID에 따라 모든 카트 아이템을 조회
    @Override
    public List<CartItem> findCartItemsByMemberId(Long memberId) {
        QCartItem qCartItem = QCartItem.cartItem;
        return queryFactory
                .selectFrom(qCartItem)
                .where(qCartItem.cart.member.memberId.eq(memberId))
                .fetch();
    }

    // 새로운 카트 아이템을 추가
    @Override
    public CartItem addCartItem(Long memberId, Item item, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setQuantity(quantity);
        return entityManager.merge(cartItem);
    }


    // 카트 아이템 삭제
    @Transactional
    public void deleteCartItemById(Long itemId) {
        QCartItem qCartItem = QCartItem.cartItem;
        queryFactory
                .delete(qCartItem)
                .where(qCartItem.id.eq(itemId))
                .execute();
    }


    // 카트 내 모든 아이템 삭제
    @Override
    public void clearCartByMemberId(Long memberId) {
        QCartItem qCartItem = QCartItem.cartItem;
        queryFactory
                .delete(qCartItem)
                .where(qCartItem.cart.member.memberId.eq(memberId))
                .execute();
    }

    // 이미지와 함께 아이템을 가져오는 메서드
    public List<Item> findItemsWithImages() {
        QItem item = QItem.item;
        QItemImage itemImage = QItemImage.itemImage;
        return queryFactory
                .selectFrom(item)
                .leftJoin(item.image, itemImage).fetchJoin()
                .distinct()
                .fetch();
    }



    @Override
    public Optional<CartItem> findCartItemByMemberId(Long memberId) {
        QCartItem cartItem = QCartItem.cartItem;
        return Optional.ofNullable(queryFactory
                .selectFrom(cartItem)
                .where(cartItem.cart.member.memberId.eq(memberId))
                .fetchOne());
    }
    @Override
    public List<CartItem> findUnpaidCartItemsByMemberId(Long memberId) {
        QCartItem qCartItem = QCartItem.cartItem;
        QCart qCart = QCart.cart;

        return queryFactory
                .selectFrom(qCartItem)
                .join(qCartItem.cart, qCart)
                .where(qCart.member.memberId.eq(memberId)
                        .and(qCart.isPaid.eq(false)))
                .fetch();
    }

    @Override
    public Optional<CartItem> findByMemberIdAndItemIdAndVariantIdAndSizeId(Long memberId, Long itemId, Long variantId, Long sizeId) {
        QCartItem cartItem = QCartItem.cartItem;
        return Optional.ofNullable(
                queryFactory.selectFrom(cartItem)
                        .where(cartItem.cart.member.memberId.eq(memberId)
                                .and(cartItem.item.itemId.eq(itemId))
                                .and(cartItem.variant.id.eq(variantId))
                                .and(cartItem.size.id.eq(sizeId)))
                        .fetchOne());
    }



}
