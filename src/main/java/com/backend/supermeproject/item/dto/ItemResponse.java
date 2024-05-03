package com.backend.supermeproject.item.dto;

import java.util.List;

public record ItemResponse(
        String productName,
        Double price,
        String category,
        String description,
        List<String> images,
        List<VariantResponse> variants
) {}