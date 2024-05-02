package com.backend.supermeproject.item.dto;

import java.util.List;

public record ProductDTO(
        String productName,
        Double price,
        String category,
        String description
//        List<String> images,
//        List<VariantDTO> variants
) {}
