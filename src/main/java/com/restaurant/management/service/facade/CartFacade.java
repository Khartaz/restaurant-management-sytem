package com.restaurant.management.service.facade;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.SessionCart;
import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.impl.CartService;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public final class CartFacade {

    private CartService cartService;
    private CartMapper cartMapper;

    @Autowired
    public CartFacade(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    public Page<CartDto> getAllCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<Cart> carts = cartService.getAllCarts(currentUser, pageable);

        return cartMapper.mapToCartDtoPage(carts);
    }

    public Page<CartDto> getCustomerCarts(@CurrentUser UserPrincipal currentUser, Long id, Pageable pageable) {
        Page<Cart> carts = cartService.getCustomerCarts(currentUser, id, pageable);

        return cartMapper.mapToCartDtoPage(carts);
    }

    public CartDto getCustomerCartById(@CurrentUser UserPrincipal currentUser, Long customerId, Long cartId) {
        Cart cart = cartService.getCustomerCartById(currentUser, customerId, cartId);

        return cartMapper.mapToCartDto(cart);
    }

    public CartDto getCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        Cart cart = cartService.getCartById(currentUser, cartId);

        return cartMapper.mapToCartDto(cart);
    }

    public Page<CartDto> getSessionCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<SessionCart> carts = cartService.getSessionCarts(currentUser, pageable);

        return cartMapper.mapToCartDto(carts);
    }

    public CartDto getSessionCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        SessionCart cart = cartService.getSessionCartById(currentUser, cartId);

        return cartMapper.mapToCartDto(cart);
    }

    public CartDto getSessionCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long id) {
        SessionCart sessionCart = cartService.getSessionCartByCustomerId(currentUser, id);

        return cartMapper.mapToCartDto(sessionCart);
    }

    public ApiResponse deleteSessionCart(@CurrentUser UserPrincipal currentUser, Long cartId) {
        return cartService.deleteSessionCart(currentUser, cartId);
    }

    public CartDto removeProductFromCart(@CurrentUser UserPrincipal currentUser, Long id, RemoveProductRequest request) {
        SessionCart sessionCart = cartService.removeProductFromCart(currentUser, id, request);

        return cartMapper.mapToCartDto(sessionCart);
    }

    public CartDto openSessionCart(@CurrentUser UserPrincipal currentUser, Long id) {
        SessionCart sessionCart = cartService.openSessionCart(currentUser, id);

        return cartMapper.mapToCartDto(sessionCart);
    }

    public CartDto addToCart(@CurrentUser UserPrincipal currentUser, Long id, UpdateCartRequest request) {
        SessionCart sessionCart = cartService.addToCart(currentUser, id, request);

        return cartMapper.mapToCartDto(sessionCart);
    }

    public CartDto updateProductQuantity(@CurrentUser UserPrincipal currentUser, Long id, UpdateCartRequest request) {
        SessionCart sessionCart = cartService.updateProductQuantity(currentUser, id, request);

        return cartMapper.mapToCartDto(sessionCart);
    }
}
