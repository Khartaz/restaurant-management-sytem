package com.restaurant.management.service.impl;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.RestaurantInfo;
import com.restaurant.management.domain.SessionCart;
import com.restaurant.management.domain.archive.CustomerArchive;
import com.restaurant.management.domain.archive.LineItemArchive;
import com.restaurant.management.domain.archive.ProductArchive;
import com.restaurant.management.exception.cart.CartMessages;
import com.restaurant.management.exception.cart.CartNotFoundException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.repository.CartRepository;
import com.restaurant.management.repository.SessionCartRepository;
import com.restaurant.management.repository.archive.CustomerArchiveRepository;
import com.restaurant.management.repository.archive.ProductArchiveRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.CartService;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class CartServiceImpl implements CartService {
    private ProductArchiveRepository productArchiveRepository;
    private CustomerArchiveRepository customerArchiveRepository;
    private SessionCartRepository sessionCartRepository;
    private AccountUserRepository accountUserRepository;
    private CartRepository cartRepository;
    private CartMapper cartMapper;

    public CartServiceImpl(ProductArchiveRepository productArchiveRepository,
                           CustomerArchiveRepository customerArchiveRepository,
                           SessionCartRepository sessionCartRepository,
                           AccountUserRepository accountUserRepository,
                           CartRepository cartRepository,
                           CartMapper cartMapper) {
        this.productArchiveRepository = productArchiveRepository;
        this.customerArchiveRepository = customerArchiveRepository;
        this.sessionCartRepository = sessionCartRepository;
        this.accountUserRepository = accountUserRepository;
        this.cartMapper = cartMapper;
        this.cartRepository = cartRepository;
    }

    private AccountUser getUserById(@CurrentUser UserPrincipal currentUser) {
        return accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));
    }

    private SessionCart getSessionCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return sessionCartRepository.findByIdAndRestaurantInfoId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage() + cartId));
    }

    private ApiResponse deleteSessionCart(@CurrentUser UserPrincipal currentUser, Long cartId) {

        SessionCart sessionCart = getSessionCartById(currentUser, cartId);

        sessionCartRepository.deleteById(sessionCart.getId());

        return new ApiResponse(true, CartMessages.CART_DELETED.getMessage());
    }

    private SessionCart getSessionCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long customerId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return sessionCartRepository.findSessionCartByCustomerIdAndRestaurantInfoId(customerId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CUSTOMER_SESSION_CART_NOT_FOUND.getMessage()));
    }

    public Cart confirmCart(@CurrentUser UserPrincipal currentUser, Long customerId) {
        RestaurantInfo restaurantInfo = getUserById(currentUser).getRestaurantInfo();

        SessionCart sessionCart = getSessionCartByCustomerId(currentUser, customerId);

        Cart cart = cartMapper.mapToCart(sessionCart);

        cart.setRestaurantInfo(restaurantInfo);
        cart.getLineItems().forEach(v -> v.setRestaurantInfo(restaurantInfo));

        CustomerArchive customerArchive = cart.getCustomer();

        customerArchiveRepository.save(customerArchive);

        List<ProductArchive> productArchives = cart.getLineItems().stream()
                .map(LineItemArchive::getProduct)
                .collect(Collectors.toList());

        productArchives.forEach(v -> v.setRestaurantInfo(restaurantInfo));

        productArchiveRepository.saveAll(productArchives);

        cartRepository.save(cart);

        deleteSessionCart(currentUser, sessionCart.getId());

        return cart;
    }

    public Page<Cart> getAllCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return cartRepository.findAllByRestaurantInfoId(restaurantId, pageable);
    }

    public Page<Cart> getCustomerCarts(@CurrentUser UserPrincipal currentUser, Long id, Pageable pageable) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return cartRepository.findByCustomerIdAndRestaurantInfoId(id, restaurantId,  pageable);
    }

    public Cart getCustomerCartById(@CurrentUser UserPrincipal currentUser, Long customerId, Long cartId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return cartRepository.findByIdAndRestaurantInfoIdAndCustomerId(cartId, customerId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CUSTOMER_CART_NOT_FOUND.getMessage()));
    }

    public Cart getCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return cartRepository.findByIdAndRestaurantInfoId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage()));
    }
}
