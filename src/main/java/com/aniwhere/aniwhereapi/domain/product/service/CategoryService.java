package com.aniwhere.aniwhereapi.domain.product.service;


import com.aniwhere.aniwhereapi.domain.product.dto.CategoryDTO;
import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.entity.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getCategory();

    List<ProductDTO> getProducts(Long categoryId);

    default CategoryDTO entityToDTO(Category category) {

        return CategoryDTO.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .build();
    }
}
