package com.backend.supermeproject.item.repository;

import com.backend.supermeproject.item.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantRepository extends JpaRepository<Variant, Long> {
}
