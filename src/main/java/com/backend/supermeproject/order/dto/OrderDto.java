package com.backend.supermeproject.order.dto;

import com.backend.supermeproject.order.entity.Order;
import com.backend.supermeproject.order.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record OrderDto(
        Long orderId,
        Long memberId,
        List<OrderItemDto> items,
        BigDecimal totalPrice,
        String status
) {
    public static OrderDto fromEntity(Order order) {
        return new OrderDto(
                order.getId(),
                order.getMemberId(),
                order.getItems().stream().map(OrderItemDto::fromEntity).collect(Collectors.toList()),
                order.getTotalPrice(),
                order.getStatus()
        );
    }
}
