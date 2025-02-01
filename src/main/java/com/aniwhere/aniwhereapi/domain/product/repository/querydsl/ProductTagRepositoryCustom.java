package com.aniwhere.aniwhereapi.domain.product.repository.querydsl;

import com.aniwhere.aniwhereapi.domain.product.entity.Tag;

import java.util.List;

public interface ProductTagRepositoryCustom {

    List<Tag> findTagListByProduct(Long id);
}
