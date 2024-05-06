package com.backend.supermeproject.image.service;

import com.backend.supermeproject.image.ImageEntity.ItemImage;
import com.backend.supermeproject.image.repository.ItemImageRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImageUploadUtil {


    public static List<ItemImage> uploadImages(List<MultipartFile> files, Long memberId, ItemImageRepository itemImageRepository) {
        if (files.isEmpty()) return null;

        List<ItemImage> images = new ArrayList<>();

        for (MultipartFile file : files) {
            ItemImage image = saveImage(file, memberId, itemImageRepository);
            images.add(image);
        }
        return images;
    }

    private static ItemImage saveImage(MultipartFile file, Long memberId, ItemImageRepository itemImageRepository) {


//        String projectPath = System.getProperty("user.dir")+"/image/item";
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = projectPath + File.separator + fileName;

        try {
            // 파일 저장
            file.transferTo(new File(filePath));

            // 이미지 엔티티 생성 및 저장
            ItemImage image = ItemImage.builder()
                    .fileName(fileName)
                    .filePath(filePath)
                    .memberId(memberId)
                    .build();
            itemImageRepository.save(image);
            return image;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
