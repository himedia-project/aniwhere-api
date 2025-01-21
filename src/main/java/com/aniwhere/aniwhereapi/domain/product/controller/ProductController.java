package com.aniwhere.aniwhereapi.domain.product.controller;

import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.service.ProductService;
import com.aniwhere.aniwhereapi.util.file.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final CustomFileUtil fileUtil;
    private final ProductService productService;


    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam(required = false) String searchKeyword) {
        List<ProductDTO> productDTOList;
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            productDTOList = productService.searchProducts(searchKeyword);
        } else {
            productDTOList = productService.getAllProducts();
        }
        return ResponseEntity.ok(productDTOList);
    }


    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {
        return fileUtil.getFile(fileName);
    }

}
