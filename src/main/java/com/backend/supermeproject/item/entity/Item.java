package com.backend.supermeproject.item.entity;

import com.backend.supermeproject.image.ImageEntity.ItemImage;
import com.backend.supermeproject.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String productName;
    private Double price;
    private String category;
    private String description;
    private Long memberId;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Variant> variants;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemImage> image;

    @Builder
    public Item(String productName, Double price, String category, String description, List<Variant> variants, List<ItemImage> image, Long memberId) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.description = description;
        this.variants = variants;
        this.image = image;
        this.memberId = memberId;
    }
}
