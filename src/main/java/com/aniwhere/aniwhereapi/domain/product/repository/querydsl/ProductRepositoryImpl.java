package com.aniwhere.aniwhereapi.domain.product.repository.querydsl;

import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.entity.Product;
import com.aniwhere.aniwhereapi.domain.product.enums.Adult;
import com.aniwhere.aniwhereapi.domain.product.enums.ProductMdPick;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
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
                .where()
                .stream()
                .toList();
    }

    @Override
    public List<Product> findByDTO(ProductDTO productDTO) {
        return queryFactory
                .selectFrom(product)
                .where(
                        eqAdult(productDTO.getAdult()),
                        eqMdPick(productDTO.getMdPick()),
                        betweenReleaseDate(productDTO.getReleaseDate()),
                        containsSearchKeyword(productDTO.getSearchKeyword())
                ).fetch();
    }

    private BooleanExpression eqMdPick(ProductMdPick mdPick) {
        if(mdPick == null){
            return null;
        }
        return product.mdPick.eq(mdPick);
    }


    private BooleanExpression containsSearchKeyword(String searchKeyword) {

        if(searchKeyword == null) {
            return null;
        }
        return product.name.contains(searchKeyword)
                .or(product.category.name.contains(searchKeyword));

    }

    private BooleanExpression betweenReleaseDate(String branch) {
        if(branch == null) {
            return null;
        }
        LocalDate startDate = LocalDate.of(Integer.parseInt(branch), 1, 1);
        LocalDate endDate = LocalDate.of(Integer.parseInt(branch), 12, 31);
        return product.releaseDate.between(startDate, endDate);
    }

    private BooleanExpression eqAdult(Adult adult) {
        if (adult == null) {
            return null;
        }
        return product.adult.eq(adult);
    }

}
