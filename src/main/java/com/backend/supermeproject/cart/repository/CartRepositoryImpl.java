package com.backend.supermeproject.cart.repository;

import com.backend.supermeproject.cart.entity.Cart;
import com.backend.supermeproject.cart.entity.CartItem;
import com.backend.supermeproject.cart.entity.QCart;
import com.backend.supermeproject.cart.entity.QCartItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartRepositoryImpl implements CartRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public CartRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<Cart> findLatestActiveCartByMemberId(Long memberId) {
        QCart qCart = QCart.cart;
        Cart cart = queryFactory.selectFrom(qCart)
                .where(qCart.member.memberId.eq(memberId).and(qCart.isPaid.eq(false)))
                .orderBy(qCart.id.desc())
                .fetchFirst();
        return Optional.ofNullable(cart);
    }



}

