package com.restaurant.management.service.facade;

import com.restaurant.management.domain.ecommerce.CartOrdered;
import com.restaurant.management.domain.ecommerce.dto.CartDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.CartOrderedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public final class CartFacade {
    private CartMapper cartMapper;
    private CartOrderedService cartOrderedService;

    @Autowired
    public CartFacade(CartMapper cartMapper,
                      CartOrderedService cartOrderedService) {
        this.cartMapper = cartMapper;
        this.cartOrderedService = cartOrderedService;
    }

    public Page<CartDto> getAllCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<CartOrdered> carts = cartOrderedService.getAllCarts(currentUser, pageable);

        return cartMapper.mapToCartDtoPage(carts);
    }

    public CartDto getCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        CartOrdered cartOrdered = cartOrderedService.getCartById(currentUser, cartId);

        return cartMapper.mapToCartDto(cartOrdered);
    }

}
