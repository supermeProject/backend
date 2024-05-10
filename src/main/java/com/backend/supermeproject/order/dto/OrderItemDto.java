package com.backend.supermeproject.order.dto;

import com.backend.supermeproject.order.entity.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDto {
    private Long itemId;
    private String productName;
    private int quantity;
    private BigDecimal price;

    // 객체 변환 메서드: 엔터티에서 DTO로 변환
    public static OrderItemDto fromEntity(OrderItem orderItem) {
        return OrderItemDto.builder()
                .itemId(orderItem.getItem().getItemId())
                .productName(orderItem.getItem().getProductName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }
}
