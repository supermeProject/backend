package com.backend.supermeproject.item.dto;

import com.backend.supermeproject.item.entity.Size;

public record SizeResponse(
        String size,
        Integer stock
) {
    public static SizeResponse fromEntity(Size size) {
        return new SizeResponse(size.getSize(), size.getStock());
    }
}
