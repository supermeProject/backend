package com.backend.supermeproject.order.entity;

import com.backend.supermeproject.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)  // 관계 매핑 추가
    @JoinColumn(name = "member_id", nullable = false)

    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

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

    @PrePersist
    public void prePersist() {
        this.paymentDate = LocalDate.now();  // 현재 날짜를 저장
    }
}
