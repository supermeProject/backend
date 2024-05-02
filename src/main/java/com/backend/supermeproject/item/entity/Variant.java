package com.backend.supermeproject.item.entity;

import com.backend.supermeproject.item.entity.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Variant {

    @Id
    @Column(name = "variant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;


    @OneToMany(mappedBy = "variant")
    private List<Size> sizes;


}
