package com.aniwhere.aniwhereapi.domain.product.repository.querydsl;

import com.aniwhere.aniwhereapi.domain.product.entity.Product;
import com.aniwhere.aniwhereapi.domain.product.entity.ProductTag;

import java.util.List;

public interface TagRepositoryCustom {

    List<Long> findByTag(Long id);
}
