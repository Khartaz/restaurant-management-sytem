package com.restaurant.management.service.facade;

import com.restaurant.management.domain.ecommerce.Cart;
import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public final class CartFacade {
    private CartMapper cartMapper;
    private CartService cartService;

    @Autowired
    public CartFacade(CartMapper cartMapper,
                      CartService cartService) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
    }

    public Page<CartDto> getAllCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<Cart> carts = cartService.getAllCarts(currentUser, pageable);

        return cartMapper.mapToCartDtoPage(carts);
    }

    public CartDto getCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        Cart cart = cartService.getCartById(currentUser, cartId);

        return cartMapper.mapToCartDto(cart);
    }

}
