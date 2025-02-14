package com.aniwhere.aniwhereapi.domain.product.dto;

import com.aniwhere.aniwhereapi.domain.product.enums.Adult;
import com.aniwhere.aniwhereapi.domain.product.enums.ProductMdPick;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProductResponseDTO {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String name;
    private Integer price;
    private String story;
    private Adult adult;
    private ProductMdPick mdPick;
    private Integer totalEpisode;
    private String releaseDate;
    private String manufacturer;

    private List<String> imagePathList;

    // 파일 업로드한 url 응답값
    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;
}


