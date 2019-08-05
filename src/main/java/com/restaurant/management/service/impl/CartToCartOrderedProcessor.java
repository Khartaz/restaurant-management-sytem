package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.*;
import com.restaurant.management.exception.cart.CartMessages;
import com.restaurant.management.exception.cart.CartNotFoundException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.repository.CartOrderedRepository;
import com.restaurant.management.repository.CartRepository;
import com.restaurant.management.repository.CustomerOrderedRepository;
import com.restaurant.management.repository.ProductOrderedRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("Duplicates")
public final class CartToCartOrderedProcessor {
    private CustomerOrderedRepository customerOrderedRepository;
    private ProductOrderedRepository productOrderedRepository;
    private CartOrderedRepository cartOrderedRepository;
    private CartRepository cartRepository;
    private AccountUserRepository accountUserRepository;

    @Autowired
    public CartToCartOrderedProcessor(CustomerOrderedRepository customerOrderedRepository,
                                      ProductOrderedRepository productOrderedRepository,
                                      CartOrderedRepository cartOrderedRepository,
                                      CartRepository cartRepository,
                                      AccountUserRepository accountUserRepository) {
        this.customerOrderedRepository = customerOrderedRepository;
        this.productOrderedRepository = productOrderedRepository;
        this.cartOrderedRepository = cartOrderedRepository;
        this.cartRepository = cartRepository;
        this.accountUserRepository = accountUserRepository;
    }

    CartOrdered processSessionCartToCart(@CurrentUser UserPrincipal currentUser, Long customerId) {
        Company company = getUserById(currentUser).getCompany();

        Cart cart = getRestaurantSessionCartByCustomerId(currentUser, customerId);

        CartOrdered cartOrdered = mapToCart(cart);

        cartOrdered.setCompany(company);
        cartOrdered.getLineItemsOrdered().forEach(v -> v.setRestaurantInfo(company));

        CustomerOrdered customerOrdered = cartOrdered.getCustomerOrdered();
        customerOrdered.setCompany(company);

        customerOrderedRepository.save(customerOrdered);

        List<ProductOrdered> productOrdereds = cartOrdered.getLineItemsOrdered().stream()
                .map(LineItemOrdered::getProductOrdered)
                .collect(Collectors.toList());

        productOrdereds.forEach(v -> v.setCompany(company));

        productOrderedRepository.saveAll(productOrdereds);

        cartOrderedRepository.save(cartOrdered);

        deleteSessionCart(currentUser, cart.getId());

        return cartOrdered;
    }

    private CartOrdered mapToCart(Cart cart) {
        return new CartOrdered(
                cart.isOpen(),
                cart.getTotalPrice(),
                this.mapToCustomerOrdered(cart),
                cart.getLineItems().stream()
                        .map(this::mapToLineItemOrdered)
                        .collect(Collectors.toList())
        );
    }

    private CustomerOrdered mapToCustomerOrdered(Cart cart) {
        CustomerOrderedAddress address = mapToCustomerOrderedAddress(cart);

        return new CustomerOrdered.CustomerOrderedBuilder()
                .setName(cart.getCustomer().getName())
                .setLastName(cart.getCustomer().getLastName())
                .setEmail(cart.getCustomer().getEmail())
                .setPhone(cart.getCustomer().getPhone())
                .setCompany(cart.getCustomer().getCompany())
                .setCustomerOrderedAddress(address)
                .build();
    }

    private CustomerOrderedAddress mapToCustomerOrderedAddress(Cart cart) {
        return new CustomerOrderedAddress.CustomerOrderedAddressBuilder()
                .setStreetAndNumber(cart.getCustomer().getCustomerAddress().getStreetAndNumber())
                .setPostCode(cart.getCustomer().getCustomerAddress().getPostCode())
                .setCity(cart.getCustomer().getCustomerAddress().getCity())
                .setCountry(cart.getCustomer().getCustomerAddress().getCountry())
                .build();
    }

    private LineItemOrdered mapToLineItemOrdered(final LineItem lineItem) {
        return new LineItemOrdered(
                lineItem.getQuantity(),
                lineItem.getPrice(),
                this.mapToProductOrdered(lineItem.getProduct())
        );
    }

    private ProductOrdered mapToProductOrdered(final Product product) {
        return new ProductOrdered(
                product.getName(),
                product.getCategory(),
                product.getPrice()
        );
    }

    private AccountUser getUserById(@CurrentUser UserPrincipal currentUser) {
        return accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));
    }

    private Cart getRestaurantSessionCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long customerId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return cartRepository.findSessionCartByCustomerIdAndCompanyId(customerId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CUSTOMER_SESSION_CART_NOT_FOUND.getMessage()));
    }

    private ApiResponse deleteSessionCart(@CurrentUser UserPrincipal currentUser, Long cartId) {

        Cart cart = getSessionCartById(currentUser, cartId);

        cartRepository.deleteById(cart.getId());

        return new ApiResponse(true, CartMessages.CART_DELETED.getMessage());
    }

    private Cart getSessionCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return cartRepository.findByIdAndCompanyId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage() + cartId));
    }
}
