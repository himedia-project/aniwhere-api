package com.aniwhere.aniwhereapi.domain.product.repository.querydsl;

import com.aniwhere.aniwhereapi.domain.product.entity.Tag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.aniwhere.aniwhereapi.domain.product.entity.QProductTag.productTag;
import static com.aniwhere.aniwhereapi.domain.product.entity.QTag.tag;

@Slf4j
@RequiredArgsConstructor
public class ProductTagRepositoryImpl implements ProductTagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Tag> findTagListByProduct(Long id) {
        return jpaQueryFactory
                .select(tag)
                .from(productTag)
                .leftJoin(tag).on(productTag.tag.id.eq(tag.id)).fetchJoin()
                .where(eqProductId(id))
                .fetch();
    }

    private BooleanExpression eqProductId(Long id) {
        if (id == null) {
            return null;
        }
        return productTag.product.id.eq(id);
    }
}
