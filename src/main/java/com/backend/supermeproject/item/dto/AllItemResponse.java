package com.backend.supermeproject.item.dto;

import java.util.List;
public record AllItemResponse(
        Long id,
        String title,
        String description,
        double price,
        String category,
        List<String> images
) {
}
