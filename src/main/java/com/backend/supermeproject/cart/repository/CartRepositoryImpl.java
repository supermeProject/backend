package com.backend.supermeproject.cart.repository;

import com.backend.supermeproject.cart.entity.Cart;
import com.backend.supermeproject.cart.entity.QCart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository @RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {
    private final JPAQueryFactory queryFactory;


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

