package com.restaurant.management.service.facade;

import com.restaurant.management.domain.ecommerce.Cart;
import com.restaurant.management.domain.ecommerce.dto.CartDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.CartService;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class SessionCartFacade {

    private CartService cartService;
    private CartMapper cartMapper;

    @Autowired
    public SessionCartFacade(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    public Page<CartDto> getSessionCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<Cart> carts = cartService.getCarts(currentUser, pageable);

        return cartMapper.mapToCartDto(carts);
    }

    public CartDto getSessionCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        Cart cart = cartService.getCartById(currentUser, cartId);

        return cartMapper.mapToCartDto(cart);
    }

    public CartDto getSessionCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long id) {
        Cart cart = cartService.getCartByCustomerId(currentUser, id);

        return cartMapper.mapToCartDto(cart);
    }

    public ApiResponse deleteSessionCart(@CurrentUser UserPrincipal currentUser, Long cartId) {
        return cartService.deleteCart(currentUser, cartId);
    }

    public CartDto removeProductFromCart(@CurrentUser UserPrincipal currentUser, Long id, RemoveProductRequest request) {
        Cart cart = cartService.removeProductFromCart(currentUser, id, request);

        return cartMapper.mapToCartDto(cart);
    }

    public CartDto updateProductQuantity(@CurrentUser UserPrincipal currentUser, Long customerId, UpdateCartRequest request) {
        Cart cart = cartService.updateProductQuantity(currentUser, customerId, request);

        return cartMapper.mapToCartDto(cart);
    }

    public CartDto addToCart(@CurrentUser UserPrincipal currentUser, Long customerId, UpdateCartRequest request) {
        Optional<Cart> sessionCart = cartService.getCart(currentUser, customerId);

        if (!sessionCart.isPresent()) {
            cartService.openCart(currentUser, customerId);
        }

        sessionCart = Optional.ofNullable(cartService.addToCart(currentUser, customerId, request));

        return cartMapper.mapToCartDto(sessionCart.get());
    }
}
