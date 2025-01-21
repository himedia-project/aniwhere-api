package com.aniwhere.aniwhereapi.domain.cart.service;

import com.aniwhere.aniwhereapi.domain.cart.dto.CartItemDTO;
import com.aniwhere.aniwhereapi.domain.cart.dto.CartItemListDTO;
import com.aniwhere.aniwhereapi.domain.cart.entity.Cart;
import com.aniwhere.aniwhereapi.domain.cart.entity.CartItem;
import com.aniwhere.aniwhereapi.domain.cart.repository.CartItemRepository;
import com.aniwhere.aniwhereapi.domain.cart.repository.CartRepository;
import com.aniwhere.aniwhereapi.domain.member.entity.Member;
import com.aniwhere.aniwhereapi.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CartItemListDTO> getCartItemList(String email) {
        log.info("getCartItemList..........");
        return cartItemRepository.getItemsOfCartList(email).stream()
                .map(this::entityToDTO)
                .toList();
    }

    @Override
    public List<CartItemListDTO> addCartItem(CartItemDTO cartItemDTO) {
        log.info("addCartItem..........");
        String email = cartItemDTO.getEmail();
        Long pno = cartItemDTO.getProductId();

        Cart cart = this.getCart(email);
        CartItem cartItem = cartItemRepository.getItemOfPno(email, pno);

        if (cartItem == null) {
            Product product = Product.builder().id(pno).build();
            cartItem = CartItem.builder().product(product).cart(cart).build();
            cartItemRepository.save(cartItem);
        }

        return getCartItemList(email);
    }

    @Override
    public List<CartItemListDTO> removeCartItem(Long cartItemId) {
        log.info("removeCartItem..........");
        Long cartId = cartItemRepository.getCartFromItem(cartItemId);

        log.info("cart id: " + cartId);

        cartItemRepository.deleteById(cartItemId);

        return cartItemRepository.getItemsOfCartByCartId(cartId).stream()
                .map(this::entityToDTO)
                .toList();
    }

    private Cart getCart(String email) {
        Cart cart = null;

        Optional<Cart> result = cartRepository.getCartOfMember(email);
        if (result.isEmpty()) {
            log.info("Cart of the member is not exist!!");
            Member member = Member.builder().email(email).build();
            Cart tempCart = Cart.builder().member(member).build();
            cart = cartRepository.save(tempCart);
        } else {
            cart = result.get();
        }

        return cart;
    }
}
