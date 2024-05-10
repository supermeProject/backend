package com.backend.supermeproject.item.dto;

import java.math.BigDecimal;
import java.util.List;
public record AllItemResponse(
        Long id,
        String title,
        String description,
        BigDecimal price,
        String category,
        List<String> images
) {
}
