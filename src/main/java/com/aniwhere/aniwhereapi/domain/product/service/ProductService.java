package com.aniwhere.aniwhereapi.domain.product.service;


import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.entity.Product;

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

    List<ProductDTO> list(ProductDTO productDTO);

//    default ProductDTO entityToDTO(Product product) {
//        ProductDTO productDTO = ProductDTO.builder()
//                .id(product.getId())
//                .categoryId(product.getCategory().getId())
//                .name(product.getName())
//                .price(product.getPrice())
//                .story(product.getStory())
//                .mdPick(product.getMdPick())
//                .adult(product.getAdult())
//                .totalEpisode(product.getTotalEpisode())
//                .tagStrList(product.getProductTagList().stream().map(
//                        productTag -> productTag.getTag().getName()
//                ).collect(Collectors.toList()))
//                .releaseDate(product.getReleaseDate().toString())
//                .manufacturer(product.getManufacturer())
//                .imagePathList(product.getImageList().stream().map(
//                        productImage -> productImage.getImageName()
//                ).collect(Collectors.toList()))
//                .build();
//        return productDTO;
//    }

}
