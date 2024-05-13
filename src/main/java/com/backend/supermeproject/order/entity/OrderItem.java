package com.backend.supermeproject.order.entity;

import com.backend.supermeproject.item.entity.Item;
import com.backend.supermeproject.item.entity.Size;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;  // Size 엔터티 참조

    private int quantity;
    private BigDecimal price;

    // 주문 항목 생성 메서드를 Builder 패턴으로 대체
    public static OrderItem createOrderItem(Item item, Size size, int quantity) {
        BigDecimal totalPrice = item.getPrice().multiply(BigDecimal.valueOf(quantity));

        return OrderItem.builder()
                .item(item)
                .size(size)
                .quantity(quantity)
                .price(totalPrice)  // 계산된 총 가격을 설정
                .build();
    }
}
