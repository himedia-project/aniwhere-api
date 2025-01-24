package com.aniwhere.aniwhereapi.domain.product.dto;

import com.aniwhere.aniwhereapi.domain.product.enums.Adult;
import com.aniwhere.aniwhereapi.domain.product.enums.ProductMdPick;
import com.aniwhere.aniwhereapi.domain.product.enums.ProductNew;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProductRequestDTO {
    private Long id;
    private Long categoryId;
    private Adult adult;
    private ProductMdPick mdPick;
    private String releaseDate;
    private ProductNew isNew;
    private String searchKeyword;

    // 상품 태그 문자열 리스트 "액션,스포츠,연애"
    private List<String> tagStrList;
}
