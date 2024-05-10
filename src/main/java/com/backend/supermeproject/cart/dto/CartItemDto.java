package com.backend.supermeproject.cart.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class CartItemDto {
    private Long itemId;
    private String productName;
    private Long variantId;
    private String color;
    private Long sizeId;
    private String size;
    private int quantity;
    private BigDecimal price;
    private String imageURL;
}
