package com.restaurant.management.service.facade;

import com.restaurant.management.domain.ecommerce.SessionCart;
import com.restaurant.management.domain.ecommerce.dto.CartDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.SessionCartService;
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

    private SessionCartService sessionCartService;
    private CartMapper cartMapper;

    @Autowired
    public SessionCartFacade(SessionCartService sessionCartService, CartMapper cartMapper) {
        this.sessionCartService = sessionCartService;
        this.cartMapper = cartMapper;
    }

    public Page<CartDto> getSessionCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<SessionCart> carts = sessionCartService.getSessionCarts(currentUser, pageable);

        return cartMapper.mapToCartDto(carts);
    }

    public CartDto getSessionCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        SessionCart cart = sessionCartService.getSessionCartById(currentUser, cartId);

        return cartMapper.mapToCartDto(cart);
    }

    public CartDto getSessionCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long id) {
        SessionCart sessionCart = sessionCartService.getSessionCartByCustomerId(currentUser, id);

        return cartMapper.mapToCartDto(sessionCart);
    }

    public ApiResponse deleteSessionCart(@CurrentUser UserPrincipal currentUser, Long cartId) {
        return sessionCartService.deleteSessionCart(currentUser, cartId);
    }

    public CartDto removeProductFromCart(@CurrentUser UserPrincipal currentUser, Long id, RemoveProductRequest request) {
        SessionCart sessionCart = sessionCartService.removeProductFromCart(currentUser, id, request);

        return cartMapper.mapToCartDto(sessionCart);
    }

    public CartDto updateProductQuantity(@CurrentUser UserPrincipal currentUser, Long customerId, UpdateCartRequest request) {
        SessionCart sessionCart = sessionCartService.updateProductQuantity(currentUser, customerId, request);

        return cartMapper.mapToCartDto(sessionCart);
    }

    public CartDto addToCart(@CurrentUser UserPrincipal currentUser, Long customerId, UpdateCartRequest request) {
        Optional<SessionCart> sessionCart = sessionCartService.getSessionCart(currentUser, customerId);

        if (!sessionCart.isPresent()) {
            sessionCartService.openSessionCart(currentUser, customerId);
        }

        sessionCart = Optional.ofNullable(sessionCartService.addToCart(currentUser, customerId, request));

        return cartMapper.mapToCartDto(sessionCart.get());
    }
}
