package com.backend.supermeproject.order.repository;

import com.backend.supermeproject.order.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {
    @EntityGraph(attributePaths = {"items"})
    Optional<Order> findByIdAndMemberId(Long orderId, Long memberId);
    List<Order> findAllByMemberId(Long memberId);
}
