package com.backend.supermeproject.order.dto;

import com.backend.supermeproject.order.entity.OrderItem;
import com.backend.supermeproject.global.util.ImageUtils;

import java.math.BigDecimal;

public record OrderItemDto(
        Long itemId,
        String productName,
        Long variantId,
        String color,
        Long sizeId,
        String size,
        Integer quantity,
        BigDecimal price,
        String imageURL
) {
    public static OrderItemDto fromEntity(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getItem().getItemId(),
                orderItem.getItem().getProductName(),
                orderItem.getSize().getVariant().getId(),
                orderItem.getSize().getVariant().getColor(),
                orderItem.getSize().getId(),
                orderItem.getSize().getSize(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                ImageUtils.getFirstImageUrl(orderItem.getItem().getImage())
        );
    }
}
