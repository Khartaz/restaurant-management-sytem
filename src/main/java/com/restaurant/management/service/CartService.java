package com.restaurant.management.service;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {

    Cart confirmCart(@CurrentUser UserPrincipal currentUser, Long customerId);

    Page<Cart> getAllCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    Page<Cart> getCustomerCarts(@CurrentUser UserPrincipal currentUser, Long id, Pageable pageable);

    Cart getCustomerCartById(@CurrentUser UserPrincipal currentUser, Long customerId, Long cartId);

    Cart getCartById(@CurrentUser UserPrincipal currentUser, Long cartId);

}
