package com.backend.supermeproject.cart.dto;

import com.backend.supermeproject.cart.entity.CartItem;
import com.backend.supermeproject.global.util.ImageUtils;

import java.math.BigDecimal;

public record CartItemDto(
        Long itemId,
        String productName,
        Long variantId,
        String color,
        Long sizeId,
        String size,
        int quantity,
        BigDecimal price,
        String imageURL
) {
    public static CartItemDto fromEntity(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getItem().getItemId(),
                cartItem.getItem().getProductName(),
                cartItem.getVariant().getId(),
                cartItem.getVariant().getColor(),
                cartItem.getSize().getId(),
                cartItem.getSize().getSize(),
                cartItem.getQuantity(),
                cartItem.getItem().getPrice(),
                ImageUtils.getFirstImageUrl(cartItem.getItem().getImage())

        );
    }
}
