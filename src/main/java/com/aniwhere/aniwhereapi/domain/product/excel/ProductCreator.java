package com.aniwhere.aniwhereapi.domain.product.excel;

import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCreator {

    private final AdminProductService productService;

    public void create(ProductDTO dto) {

        productService.register(dto);

    }

}
