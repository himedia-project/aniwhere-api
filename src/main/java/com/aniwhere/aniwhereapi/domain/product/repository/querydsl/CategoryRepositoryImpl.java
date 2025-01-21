package com.aniwhere.aniwhereapi.domain.product.repository.querydsl;

import com.aniwhere.aniwhereapi.domain.product.entity.Product;
import com.aniwhere.aniwhereapi.domain.product.repository.CategoryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.aniwhere.aniwhereapi.domain.product.entity.QProduct.product;

@Slf4j
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findListByCategory(Long categoryId) {
        return queryFactory
                .selectFrom(product)
                .where(product.category.id.eq(categoryId))
                .stream()
                .toList();
    }
}
