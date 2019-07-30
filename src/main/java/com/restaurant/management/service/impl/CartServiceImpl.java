package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.Cart;
import com.restaurant.management.domain.ecommerce.SessionCart;
import com.restaurant.management.exception.cart.CartMessages;
import com.restaurant.management.exception.cart.CartNotFoundException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.repository.CartRepository;
import com.restaurant.management.repository.SessionCartRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.CartService;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class CartServiceImpl implements CartService {
    private SessionCartRepository sessionCartRepository;
    private AccountUserRepository accountUserRepository;
    private CartRepository cartRepository;

    private SessionCartToCartProcessor processor;

    public CartServiceImpl(SessionCartRepository sessionCartRepository,
                           AccountUserRepository accountUserRepository,
                           CartRepository cartRepository,
                           SessionCartToCartProcessor processor) {
        this.sessionCartRepository = sessionCartRepository;
        this.accountUserRepository = accountUserRepository;
        this.cartRepository = cartRepository;
        this.processor = processor;
    }

    private AccountUser getUserById(@CurrentUser UserPrincipal currentUser) {
        return accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));
    }

    private SessionCart getSessionCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return sessionCartRepository.findByIdAndCompanyId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage() + cartId));
    }

    private ApiResponse deleteSessionCart(@CurrentUser UserPrincipal currentUser, Long cartId) {

        SessionCart sessionCart = getSessionCartById(currentUser, cartId);

        sessionCartRepository.deleteById(sessionCart.getId());

        return new ApiResponse(true, CartMessages.CART_DELETED.getMessage());
    }

    private SessionCart getRestaurantSessionCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long customerId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return sessionCartRepository.findSessionCartByCustomerIdAndCompanyId(customerId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CUSTOMER_SESSION_CART_NOT_FOUND.getMessage()));
    }

    public Cart processSessionCartToCart(@CurrentUser UserPrincipal currentUser, Long customerId) {
        return processor.processSessionCartToCart(currentUser, customerId);
    }

    public Page<Cart> getAllCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return cartRepository.findAllByCompanyId(restaurantId, pageable);
    }

    public Cart getCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return cartRepository.findByIdAndCompanyId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage()));
    }

}
