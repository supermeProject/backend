package com.backend.supermeproject.cart.dto;

import com.backend.supermeproject.cart.entity.Cart;
import com.backend.supermeproject.cart.entity.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record CartDto(Long cartId, BigDecimal totalPrice, List<CartItemDto> cartItems) {

    public static CartDto fromEntity(Cart cart) {
        List<CartItemDto> items = cart.getItems().stream()
                .map(CartItemDto::fromEntity)
                .collect(Collectors.toList());
        return new CartDto(
                cart.getId(),
                cart.getTotalPrice(),
                items
        );
    }
}
