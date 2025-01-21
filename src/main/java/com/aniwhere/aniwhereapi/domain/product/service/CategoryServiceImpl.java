package com.aniwhere.aniwhereapi.domain.product.service;

import com.aniwhere.aniwhereapi.domain.product.dto.CategoryDTO;
import com.aniwhere.aniwhereapi.domain.product.entity.Category;
import com.aniwhere.aniwhereapi.domain.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> getCategory() {
        List<Category> dtoLists = categoryRepository.findCategory();
        return dtoLists.stream()
                .map(this::entityToDTO) // Product를 ProductDTO로 변환
                .toList();
    }

}
