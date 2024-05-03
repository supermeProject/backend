package com.backend.supermeproject.item.dto;

import java.util.List;

public record VariantResponse(
        String color,
        List<SizeResponse> sizes
) {}