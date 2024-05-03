package com.backend.supermeproject.image.ImageEntity;

import com.backend.supermeproject.item.entity.Item;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    private String fileName;
    private String filePath;

    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

}