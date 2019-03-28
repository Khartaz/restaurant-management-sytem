package com.restaurant.management.service.facade;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.SessionCart;
import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.service.CartService;
import com.restaurant.management.web.request.cart.RegisterCartRequest;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartFacade {

    private CartService cartService;
    private CartMapper cartMapper;

    @Autowired
    public CartFacade(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    public Page<CartDto> getAllCarts(Pageable pageable) {
        Page<Cart> carts = cartService.getAllCarts(pageable);

        return cartMapper.mapToCartDtoPage(carts);
    }

    public CartDto getCartByUniqueId(String uniqueId) {
        Cart cart = cartService.getCartByUniqueId(uniqueId);

        return cartMapper.mapToCartDto(cart);
    }

    public Page<CartDto> getSessionCarts(Pageable pageable) {
        Page<SessionCart> carts = cartService.getSessionCarts(pageable);

        return cartMapper.mapToCartDto(carts);
    }

    public CartDto getSessionCartByUniqueId(String uniqueId) {
        SessionCart cart = cartService.getSessionCartByUniqueId(uniqueId);

        return cartMapper.mapToCartDto(cart);
    }

    public ApiResponse deleteSessionCart(String uniqueId) {
        return cartService.deleteSessionCart(uniqueId);
    }

    public CartDto removeProductFromCart(RemoveProductRequest request) {
        SessionCart sessionCart = cartService.removeProductFromCart(request);

        return cartMapper.mapToCartDto(sessionCart);
    }

    public CartDto openSessionCart(RegisterCartRequest request) {
        SessionCart sessionCart = cartService.openSessionCart(request);

        return cartMapper.mapToCartDto(sessionCart);
    }

    public CartDto addToCart(UpdateCartRequest request) {
        SessionCart sessionCart = cartService.addToCart(request);

        return cartMapper.mapToCartDto(sessionCart);
    }

    public CartDto updateProductQuantity(UpdateCartRequest request) {
        SessionCart sessionCart = cartService.updateProductQuantity(request);

        return cartMapper.mapToCartDto(sessionCart);
    }
}
