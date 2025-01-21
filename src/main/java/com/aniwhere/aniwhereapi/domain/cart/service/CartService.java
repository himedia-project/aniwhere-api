package com.aniwhere.aniwhereapi.domain.cart.service;

import com.aniwhere.aniwhereapi.domain.cart.dto.CartItemDTO;
import com.aniwhere.aniwhereapi.domain.cart.dto.CartItemListDTO;
import com.aniwhere.aniwhereapi.domain.cart.entity.CartItem;

import java.util.List;

public interface CartService {

    List<CartItemListDTO> getCartItemList(String email);

    List<CartItemListDTO> addCartItem(CartItemDTO cartItemDTO);

    List<CartItemListDTO> removeCartItem(Long cartItemId);

    default CartItemListDTO entityToDTO(CartItem cartItem) {
        return CartItemListDTO.builder()
                .cartItemId(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getName())
                .price(cartItem.getProduct().getPrice()) // discountPrice 필드 제거
                .imageName(cartItem.getProduct().getImageList().get(0).getImageName())
                .build();
    }
}
