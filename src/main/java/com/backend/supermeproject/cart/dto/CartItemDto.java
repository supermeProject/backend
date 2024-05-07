package com.backend.supermeproject.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class CartItemDto {
    private Long itemId;
    private String productName;
    private String color;
    private String size;
    private int quantity;
    private double price;
    private String imageURL;
}
