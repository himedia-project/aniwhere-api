package com.aniwhere.aniwhereapi.domain.product.service;


import com.aniwhere.aniwhereapi.domain.product.dto.ProductRequestDTO;
import com.aniwhere.aniwhereapi.domain.product.dto.ProductResponseDTO;
import com.aniwhere.aniwhereapi.domain.product.dto.TagDTO;
import com.aniwhere.aniwhereapi.domain.product.entity.Product;
import com.aniwhere.aniwhereapi.domain.product.entity.ProductImage;

import java.util.List;
import java.util.stream.Collectors;

public interface ProductService {
//    List<ProductDTO> searchProducts(String searchKeyword);
//
//    List<ProductDTO> getAllProducts();
//
//    List<ProductDTO> getYearProducts(String branch);
//
//    List<ProductDTO> getAdultProducts();

    ProductResponseDTO getProduct(Long id);

    List<ProductResponseDTO> list(ProductRequestDTO requestDTO);

    List<TagDTO> tagList(Long id);

    default ProductResponseDTO entityToDTO(Product product) {
        ProductResponseDTO responseDTO = ProductResponseDTO.builder()
                .id(product.getId())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .name(product.getName())
                .price(product.getPrice())
                .story(product.getStory())
                .mdPick(product.getMdPick())
                .adult(product.getAdult())
                .totalEpisode(product.getTotalEpisode())
                .releaseDate(product.getReleaseDate().toString())
                .manufacturer(product.getManufacturer())
                .uploadFileNames(product.getImageList().stream().map(ProductImage::getImageName).collect(Collectors.toList()))
                .imagePathList(product.getImageList().stream().map(productImage ->
                        productImage.getImageName()).collect(Collectors.toList()))
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .build();
        return responseDTO;
    }

}
