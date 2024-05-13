package com.backend.supermeproject.global.util;

import com.backend.supermeproject.image.ImageEntity.ItemImage;

import java.util.List;

public class ImageUtils {
    /**
     * 주어진 이미지 리스트에서 첫 번째 이미지의 URL을 반환합니다.
     * @param images 이미지 엔티티의 리스트
     * @return 첫 번째 이미지의 URL, 없다면 null
     */
    public static String getFirstImageUrl(List<ItemImage> images) {
        // 이미지 리스트 중 첫 번째 이미지의 URL을 반환
        if (images == null || images.isEmpty()) {
            return "이미지가 없습니다";
        }
        return images.get(0).getFilePath();
    }

}
