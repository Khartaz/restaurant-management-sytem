package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.CartOrdered;
import com.restaurant.management.domain.ecommerce.Cart;
import com.restaurant.management.exception.cart.CartMessages;
import com.restaurant.management.exception.cart.CartNotFoundException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.repository.CartOrderedRepository;
import com.restaurant.management.repository.CartRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.CartOrderedService;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class CartOrderedServiceImpl implements CartOrderedService {
    private CartRepository cartRepository;
    private AccountUserRepository accountUserRepository;
    private CartOrderedRepository cartOrderedRepository;

    private CartToCartOrderedProcessor processor;

    public CartOrderedServiceImpl(CartRepository cartRepository,
                                  AccountUserRepository accountUserRepository,
                                  CartOrderedRepository cartOrderedRepository,
                                  CartToCartOrderedProcessor processor) {
        this.cartRepository = cartRepository;
        this.accountUserRepository = accountUserRepository;
        this.cartOrderedRepository = cartOrderedRepository;
        this.processor = processor;
    }

    private AccountUser getUserById(@CurrentUser UserPrincipal currentUser) {
        return accountUserRepository.findByIdAndIsDeletedIsFalse(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));
    }

    private Cart getSessionCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return cartRepository.findByIdAndCompanyId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage() + cartId));
    }

    private ApiResponse deleteSessionCart(@CurrentUser UserPrincipal currentUser, Long cartId) {

        Cart cart = getSessionCartById(currentUser, cartId);

        cartRepository.deleteById(cart.getId());

        return new ApiResponse(true, CartMessages.CART_DELETED.getMessage());
    }

    private Cart getRestaurantSessionCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long customerId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return cartRepository.findCartByCustomerIdAndCompanyId(customerId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CUSTOMER_SESSION_CART_NOT_FOUND.getMessage()));
    }

    public CartOrdered processCartToCartOrdered(@CurrentUser UserPrincipal currentUser, Long customerId) {
        return processor.processSessionCartToCart(currentUser, customerId);
    }

    public Page<CartOrdered> getAllCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return cartOrderedRepository.findAllByCompanyId(restaurantId, pageable);
    }

    public CartOrdered getCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return cartOrderedRepository.findByIdAndCompanyId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage()));
    }

}
