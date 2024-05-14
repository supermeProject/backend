package com.backend.supermeproject.item.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private Variant variant;

    @Builder
    public Size(String size, Integer stock, Variant variant) {
        this.size = size;
        this.stock = stock;
        this.variant = variant;
    }


}