package com.backend.supermeproject.order.dto;

import com.backend.supermeproject.order.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class OrderDto {
    private Long orderId;
    private Long memberId;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
    private String status;
    private String shippingCountry;
    private String shippingAddress;
    private String shippingCity;
    private String shippingPostcode;
    private String shippingPhoneNumber;



    public static OrderDto fromEntity(Order order) {
        return OrderDto.builder()
                .orderId(order.getId())
                .memberId(order.getMemberId())
                .items(order.getItems().stream().map(OrderItemDto::fromEntity).collect(Collectors.toList()))
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .shippingCountry(order.getShippingCountry())
                .shippingAddress(order.getShippingAddress())
                .shippingCity(order.getShippingCity())
                .shippingPostcode(order.getShippingPostcode())
                .shippingPhoneNumber(order.getShippingPhoneNumber())
                .build();
    }

}
