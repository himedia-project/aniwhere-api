package com.aniwhere.aniwhereapi.domain.product.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CategoryDTO {
    private Long categoryId;
    private String name;
}
