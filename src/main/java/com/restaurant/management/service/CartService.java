package com.restaurant.management.service;

import com.restaurant.management.domain.ecommerce.Cart;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CartService {

    Cart openCart(@CurrentUser UserPrincipal currentUser, Long customerId);

    Cart addToCart(@CurrentUser UserPrincipal currentUser, Long customerId, UpdateCartRequest request);

    Cart updateProductQuantity(@CurrentUser UserPrincipal currentUser, Long customerId, UpdateCartRequest request);

    Cart removeProductFromCart(@CurrentUser UserPrincipal currentUser, Long id, RemoveProductRequest request);

    Page<Cart> getCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    Cart getCartById(@CurrentUser UserPrincipal currentUser, Long cartId);

    Cart getCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long id);

    Optional<Cart> getCart(@CurrentUser UserPrincipal currentUser, Long customerId);

    ApiResponse deleteCart(@CurrentUser UserPrincipal currentUser, Long cartId);

    ApiResponse deleteLineItem(Long id);

}
