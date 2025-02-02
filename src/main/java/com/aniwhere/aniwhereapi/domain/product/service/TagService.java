package com.aniwhere.aniwhereapi.domain.product.service;

import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.dto.TagDTO;
import com.aniwhere.aniwhereapi.domain.product.entity.Tag;

import java.util.List;

public interface TagService {

    List<ProductDTO> list(Long id);

    default TagDTO entityToDTO(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
