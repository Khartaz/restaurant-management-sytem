package com.restaurant.management.service;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.SessionCart;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {

    SessionCart openSessionCart(@CurrentUser UserPrincipal currentUser, Long id);

    SessionCart addToCart(@CurrentUser UserPrincipal currentUser, Long customerId, UpdateCartRequest request);

    SessionCart updateProductQuantity(@CurrentUser UserPrincipal currentUser, Long id, UpdateCartRequest request);

    SessionCart removeProductFromCart(@CurrentUser UserPrincipal currentUser, Long id, RemoveProductRequest request);

    Cart confirmCart(@CurrentUser UserPrincipal currentUser, Long customerId);

    Page<Cart> getAllCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    Page<Cart> getCustomerCarts(@CurrentUser UserPrincipal currentUser, Long id, Pageable pageable);

    Cart getCustomerCartById(@CurrentUser UserPrincipal currentUser, Long customerId, Long cartId);

    Page<SessionCart> getSessionCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    Cart getCartById(@CurrentUser UserPrincipal currentUser, Long cartId);

    SessionCart getSessionCartById(@CurrentUser UserPrincipal currentUser, Long cartId);

    SessionCart getSessionCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long id);

    ApiResponse deleteSessionCart(@CurrentUser UserPrincipal currentUser, Long cartId);

    void deleteCart(Long cartId);

    ApiResponse deleteLineItem(Long id);

}
