package com.backend.supermeproject.image.repository;

import com.backend.supermeproject.image.ImageEntity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
}
