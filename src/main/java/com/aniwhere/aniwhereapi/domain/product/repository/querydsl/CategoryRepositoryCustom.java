package com.aniwhere.aniwhereapi.domain.product.repository.querydsl;

import com.aniwhere.aniwhereapi.domain.product.entity.Product;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Product> findListByCategory(Long categoryId);
}
