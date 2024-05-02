package com.backend.supermeproject.item.dto;

import java.util.List;

public record VariantDTO(
        String color,
        List<SizeDTO> sizes
) {}

