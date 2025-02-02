package com.aniwhere.aniwhereapi.domain.product.repository;

import com.aniwhere.aniwhereapi.domain.product.entity.ProductTag;
import com.aniwhere.aniwhereapi.domain.product.repository.querydsl.ProductTagRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long>, ProductTagRepositoryCustom {
}
