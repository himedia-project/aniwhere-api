package com.aniwhere.aniwhereapi.domain.cart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDTO {

    private Long cartItemId;

    private String email;

    @NotNull(message = "상품 번호는 필수입니다.")
    private Long productId;

}