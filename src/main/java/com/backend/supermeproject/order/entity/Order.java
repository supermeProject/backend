package com.backend.supermeproject.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime orderDate;

    private BigDecimal totalPrice;

    @Column(nullable = false)
    private String status;  // 예: "PENDING", "SHIPPED", "DELIVERED"

    @Column(nullable = true)
    private String shippingCountry;
    @Column(nullable = true)
    private String shippingAddress;
    @Column(nullable = true)
    private String shippingCity;
    @Column(nullable = true)
    private String shippingPostcode;
    @Column(nullable = true)
    private String shippingPhoneNumber;

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}
