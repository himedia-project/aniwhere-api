package com.aniwhere.aniwhereapi.domain.product.repository.querydsl;

import com.aniwhere.aniwhereapi.domain.product.entity.Product;
import com.aniwhere.aniwhereapi.domain.product.enums.Adult;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

import static com.aniwhere.aniwhereapi.domain.product.entity.QProduct.product;


@Slf4j
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findListBySearchKeyword(String searchKeyword) {
        return queryFactory
                .selectFrom(product)
                .where(product.name.contains(searchKeyword))
                .stream()
                .toList();
    }

    @Override
    public List<Product> findListByYear(String branch) {
        LocalDate startDate = LocalDate.of(Integer.parseInt(branch), 1, 1);
        LocalDate endDate = LocalDate.of(Integer.parseInt(branch), 12, 31);

        return queryFactory
                .selectFrom(product)
                .where(product.releaseDate.between(startDate, endDate))
                .stream()
                .toList();
    }

    @Override
    public List<Product> findListByAdult() {
        return queryFactory
                .selectFrom(product)
                .where(product.adult.eq(Adult.Y))
                .stream()
                .toList();
    }

}
