package com.restaurant.management.service;

import com.restaurant.management.domain.ecommerce.CartOrdered;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartOrderedService {

    CartOrdered processCartToCartOrdered(@CurrentUser UserPrincipal currentUser, Long customerId);

    Page<CartOrdered> getAllCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    CartOrdered getCartById(@CurrentUser UserPrincipal currentUser, Long cartId);

}
