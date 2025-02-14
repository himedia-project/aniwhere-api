package com.aniwhere.aniwhereapi.domain.product.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class TagDTO {
    private Long id;
    private String name;
}
