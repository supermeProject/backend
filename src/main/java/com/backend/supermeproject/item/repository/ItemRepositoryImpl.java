package com.backend.supermeproject.item.repository;


import com.backend.supermeproject.item.entity.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;


public class ItemRepositoryImpl implements ItemCustomRepository {

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public Item findItemById(Long itemId) {
        return null;
    }
}
