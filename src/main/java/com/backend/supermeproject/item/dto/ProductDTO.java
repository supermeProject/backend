package com.backend.supermeproject.item.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductDTO(
        String productName,
        BigDecimal price,
        String category,
        String description,
        List<String> images,
        List<VariantResponse> variants
) {}
