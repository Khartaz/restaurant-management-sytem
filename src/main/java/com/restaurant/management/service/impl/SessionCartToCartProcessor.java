package com.restaurant.management.service.impl;

import com.restaurant.management.domain.*;
import com.restaurant.management.domain.archive.*;
import com.restaurant.management.domain.ecommerce.*;
import com.restaurant.management.exception.cart.CartMessages;
import com.restaurant.management.exception.cart.CartNotFoundException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.repository.CartRepository;
import com.restaurant.management.repository.SessionCartRepository;
import com.restaurant.management.repository.archive.CustomerArchiveRepository;
import com.restaurant.management.repository.archive.ProductArchiveRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("Duplicates")
public final class SessionCartToCartProcessor {
    private CustomerArchiveRepository customerArchiveRepository;
    private ProductArchiveRepository productArchiveRepository;
    private CartRepository cartRepository;
    private SessionCartRepository sessionCartRepository;
    private AccountUserRepository accountUserRepository;

    @Autowired
    public SessionCartToCartProcessor(CustomerArchiveRepository customerArchiveRepository,
                                      ProductArchiveRepository productArchiveRepository,
                                      CartRepository cartRepository,
                                      SessionCartRepository sessionCartRepository,
                                      AccountUserRepository accountUserRepository) {
        this.customerArchiveRepository = customerArchiveRepository;
        this.productArchiveRepository = productArchiveRepository;
        this.cartRepository = cartRepository;
        this.sessionCartRepository = sessionCartRepository;
        this.accountUserRepository = accountUserRepository;
    }

    Cart processSessionCartToCart(@CurrentUser UserPrincipal currentUser, Long customerId) {
        RestaurantInfo restaurantInfo = getUserById(currentUser).getRestaurantInfo();

        SessionCart sessionCart = getRestaurantSessionCartByCustomerId(currentUser, customerId);

        Cart cart = mapToCart(sessionCart);

        cart.setRestaurantInfo(restaurantInfo);
        cart.getLineItems().forEach(v -> v.setRestaurantInfo(restaurantInfo));

        CustomerArchive customerArchive = cart.getCustomerArchive();
        customerArchive.setRestaurantInfo(restaurantInfo);

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

    private Cart mapToCart(SessionCart sessionCart) {
        return new Cart(
                sessionCart.isOpen(),
                sessionCart.getTotalPrice(),
                this.mapToCustomerArchive(sessionCart),
                sessionCart.getSessionLineItems().stream()
                        .map(this::mapToLineItemArchive)
                        .collect(Collectors.toList())
        );
    }

    private CustomerArchive mapToCustomerArchive(SessionCart sessionCart) {
        CustomerArchiveAddress address = mapToCustomerArchiveAddress(sessionCart);

        return new CustomerArchive.CustomerArchiveBuilder()
                .setName(sessionCart.getCustomer().getName())
                .setLastname(sessionCart.getCustomer().getLastname())
                .setEmail(sessionCart.getCustomer().getEmail())
                .setPhoneNumber(sessionCart.getCustomer().getPhoneNumber())
                .setRestaurantInfo(sessionCart.getCustomer().getRestaurantInfo())
                .setCustomerArchiveAddress(address)
                .build();
    }

    private CustomerArchiveAddress mapToCustomerArchiveAddress(SessionCart sessionCart) {
        return new CustomerArchiveAddress.CustomerArchiveAddressBuilder()
                .setStreetAndNumber(sessionCart.getCustomer().getCustomerAddress().getStreetAndNumber())
                .setPostCode(sessionCart.getCustomer().getCustomerAddress().getPostCode())
                .setCity(sessionCart.getCustomer().getCustomerAddress().getCity())
                .setCountry(sessionCart.getCustomer().getCustomerAddress().getCountry())
                .build();
    }

    private LineItemArchive mapToLineItemArchive(final SessionLineItem sessionLineItem) {
        return new LineItemArchive(
                sessionLineItem.getQuantity(),
                sessionLineItem.getPrice(),
                this.mapToProductArchive(sessionLineItem.getProduct())
        );
    }

    private ProductArchive mapToProductArchive(final Product product) {
        return new ProductArchive(
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getIngredients().stream()
                        .map(this::mapToIngredientArchive)
                        .collect(Collectors.toList())
        );
    }

    private IngredientArchive mapToIngredientArchive(final Ingredient ingredient) {
        return new IngredientArchive(
                ingredient.getName()
        );
    }

    private AccountUser getUserById(@CurrentUser UserPrincipal currentUser) {
        return accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));
    }

    private SessionCart getRestaurantSessionCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long customerId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return sessionCartRepository.findSessionCartByCustomerIdAndRestaurantInfoId(customerId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CUSTOMER_SESSION_CART_NOT_FOUND.getMessage()));
    }

    private ApiResponse deleteSessionCart(@CurrentUser UserPrincipal currentUser, Long cartId) {

        SessionCart sessionCart = getSessionCartById(currentUser, cartId);

        sessionCartRepository.deleteById(sessionCart.getId());

        return new ApiResponse(true, CartMessages.CART_DELETED.getMessage());
    }

    private SessionCart getSessionCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return sessionCartRepository.findByIdAndRestaurantInfoId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage() + cartId));
    }
}
