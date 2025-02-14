package com.aniwhere.aniwhereapi.domain.product.controller;

import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.dto.ProductRequestDTO;
import com.aniwhere.aniwhereapi.domain.product.dto.ProductResponseDTO;
import com.aniwhere.aniwhereapi.domain.product.dto.TagDTO;
import com.aniwhere.aniwhereapi.domain.product.entity.ProductTag;
import com.aniwhere.aniwhereapi.domain.product.service.ProductService;
import com.aniwhere.aniwhereapi.util.file.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
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
public class ProductController {

    private final CustomFileUtil fileUtil;
    private final ProductService productService;

    @GetMapping("/{id}/detail")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        ProductResponseDTO responseDTO = productService.getProduct(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(ProductRequestDTO requestDTO) {
        List<ProductResponseDTO> responseDTOList = productService.list(requestDTO);
        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping("/{id}/tag/list")
    public ResponseEntity<List<TagDTO>> searchProducts(@PathVariable Long id) {
        List<TagDTO> tagDTOS = productService.tagList(id);
        return ResponseEntity.ok(tagDTOS);
    }

//    @GetMapping("/list/year/{branch}")
//    public ResponseEntity<List<ProductDTO>> getYearProducts(@PathVariable String branch) {
//        return ResponseEntity.status(HttpStatus.OK).body(productService.getYearProducts(branch));
//    }
//
//    @GetMapping("/list/adult")
//    public ResponseEntity<List<ProductDTO>> getAdultProducts() {
//        return ResponseEntity.status(HttpStatus.OK).body(productService.getAdultProducts());
//    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {
        return fileUtil.getFile(fileName);
    }

}
