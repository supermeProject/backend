package com.backend.supermeproject.cart.entity;

import com.backend.supermeproject.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Data
@Entity
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    @Builder.Default
    @Column(name = "is_paid", nullable = false)
    private boolean isPaid = false;

    public Cart(Member member) {
        this.member = member;
    }
}
