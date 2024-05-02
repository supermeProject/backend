package com.backend.supermeproject.item.entity;

import jakarta.persistence.*;

@Entity
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;
    private Integer stock;




}