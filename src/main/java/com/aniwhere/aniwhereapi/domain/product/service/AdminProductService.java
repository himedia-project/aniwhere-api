package com.aniwhere.aniwhereapi.domain.product.service;


import com.aniwhere.aniwhereapi.domain.product.dto.ProductDTO;
import com.aniwhere.aniwhereapi.domain.product.entity.Category;
import com.aniwhere.aniwhereapi.domain.product.entity.Product;
import com.aniwhere.aniwhereapi.domain.product.entity.ProductImage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

// API 명세서 -> JDOC
public interface AdminProductService {


    Long register(ProductDTO productDTO);


    /**
     * Product -> ProductDTO 변환
     *
     * @param product Product
     * @return ProductDTO
     */
    default ProductDTO entityToDTO(Product product) {

        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .name(product.getName())
                .price(product.getPrice())
                .story(product.getStory())
                .adult(product.getAdult())
                .mdPick(product.getMdPick())
                .releaseDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(product.getReleaseDate()))
                .manufacturer(product.getManufacturer())
                .totalEpisode(product.getTotalEpisode())
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .build();

        List<ProductImage> imageList = product.getImageList();

        if (imageList == null || imageList.isEmpty()) {
            return productDTO;
        }

        List<String> fileNameList = imageList.stream().map(ProductImage::getImageName).toList();

        productDTO.setUploadFileNames(fileNameList);
        productDTO.setCategoryId(product.getCategory().getId());

        return productDTO;
    }

    default Product dtoToEntity(ProductDTO dto, Category category) {
        // 날짜 문자열 포맷 변환
        LocalDate releaseDate;
        try {
            if (dto.getReleaseDate().contains("-")) {
                releaseDate = LocalDate.parse(dto.getReleaseDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } else {
                // "yyyyMMdd" 형식을 LocalDate로 파싱
                releaseDate = LocalDate.parse(dto.getReleaseDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다. (예: 20201111 또는 2020-11-11)", e);
        }

        Product product = Product.builder()
                .id(dto.getId())
                .category(category)
                .name(dto.getName())
                .price(dto.getPrice())
                .story(dto.getStory())
                .adult(dto.getAdult())
                .mdPick(dto.getMdPick())
                .releaseDate(releaseDate)
                .manufacturer(dto.getManufacturer())
                .totalEpisode(dto.getTotalEpisode())
                .stockNumber(1)
                .delFlag(false)
                .build();

        //업로드 처리가 끝난 파일들의 이름 리스트
        List<String> uploadFileNames = dto.getUploadFileNames();

        if (uploadFileNames == null) {
            return product;
        }

        // 이미지 파일 업로드 처리
        uploadFileNames.forEach(product::addImageString);

        return product;
    }
}
