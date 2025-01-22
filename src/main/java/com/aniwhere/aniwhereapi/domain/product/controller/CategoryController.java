package com.aniwhere.aniwhereapi.domain.product.controller;

import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

//    @GetMapping("/list/{categoryId}")
//    public ResponseEntity<List<ProductDTO>> getProducts(@PathVariable Long categoryId) {
//        List<ProductDTO> products = categoryService.getCategoryProducts(categoryId);
//        return ResponseEntity.ok(products);
//    }
}
