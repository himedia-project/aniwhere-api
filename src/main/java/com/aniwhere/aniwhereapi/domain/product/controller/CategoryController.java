package com.aniwhere.aniwhereapi.domain.product.controller;

import com.aniwhere.aniwhereapi.domain.product.dto.CategoryDTO;
import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.dto.TagDTO;
import com.aniwhere.aniwhereapi.domain.product.service.CategoryService;
import com.aniwhere.aniwhereapi.domain.product.service.ProductService;
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
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<List<CategoryDTO>> searchProducts() {
        List<CategoryDTO> categorys = categoryService.getCategory();
        return ResponseEntity.ok(categorys);
    }

//    @GetMapping("/list/{categoryId}")
//    public ResponseEntity<List<ProductDTO>> getProducts(@PathVariable Long categoryId) {
//        List<ProductDTO> products = categoryService.getCategoryProducts(categoryId);
//        return ResponseEntity.ok(products);
//    }
}
