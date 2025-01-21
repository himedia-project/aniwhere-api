package com.aniwhere.aniwhereapi.domain.product.repository.querydsl;


import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findListBySearchKeyword(String searchKeyword);

    List<Product> findListByYear(String branch);

    List<Product> findListByAdult();

    List<Product> findByDTO(ProductDTO productDTO);
}
