package com.restaurant.management.service;

import com.restaurant.management.domain.ecommerce.Cart;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {

    Cart processSessionCartToCart(@CurrentUser UserPrincipal currentUser, Long customerId);

    Page<Cart> getAllCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    Cart getCartById(@CurrentUser UserPrincipal currentUser, Long cartId);

}
