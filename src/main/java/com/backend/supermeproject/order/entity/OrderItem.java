package com.backend.supermeproject.order.entity;

import com.backend.supermeproject.item.entity.Item;
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

    private int quantity;
    private BigDecimal price;

    // 주문 항목 생성 메서드를 Builder 패턴으로 대체
    public static OrderItem createOrderItem(Item item, int quantity) {
        BigDecimal quantityAsBigDecimal = BigDecimal.valueOf(quantity);  // int를 BigDecimal로 변환
        BigDecimal totalPrice = item.getPrice().multiply(quantityAsBigDecimal);  // 가격 계산

        return OrderItem.builder()
                .item(item)
                .quantity(quantity)
                .price(totalPrice)  // 계산된 총 가격을 설정
                .build();
    }
}
