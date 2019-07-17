package com.restaurant.management.service;

import com.restaurant.management.domain.ecommerce.SessionCart;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SessionCartService {

    SessionCart openSessionCart(@CurrentUser UserPrincipal currentUser, Long customerId);

    SessionCart addToCart(@CurrentUser UserPrincipal currentUser, Long customerId, UpdateCartRequest request);

    SessionCart updateProductQuantity(@CurrentUser UserPrincipal currentUser, Long customerId, UpdateCartRequest request);

    SessionCart removeProductFromCart(@CurrentUser UserPrincipal currentUser, Long id, RemoveProductRequest request);

    Page<SessionCart> getSessionCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    SessionCart getSessionCartById(@CurrentUser UserPrincipal currentUser, Long cartId);

    SessionCart getSessionCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long id);

    Optional<SessionCart> getSessionCart(@CurrentUser UserPrincipal currentUser, Long customerId);

    ApiResponse deleteSessionCart(@CurrentUser UserPrincipal currentUser, Long cartId);

    void deleteCart(Long cartId);

    ApiResponse deleteLineItem(Long id);

}
