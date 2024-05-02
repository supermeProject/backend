package com.backend.supermeproject.item.entity;

import com.backend.supermeproject.image.ImageEntity.ItemImage;
import com.backend.supermeproject.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String productName;
    private Double price;
    private String category;
    private String description;



}
