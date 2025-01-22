package com.aniwhere.aniwhereapi.domain.product.service;

import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;

import java.util.List;

public interface TagService {

    List<ProductDTO> list(Long id);
}
