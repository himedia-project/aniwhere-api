package com.aniwhere.aniwhereapi.domain.product.repository;

import com.aniwhere.aniwhereapi.domain.product.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
}
