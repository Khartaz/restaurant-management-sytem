package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.*;
import com.restaurant.management.exception.cart.CartExistsException;
import com.restaurant.management.exception.cart.CartMessages;
import com.restaurant.management.exception.cart.CartNotFoundException;
import com.restaurant.management.exception.customer.CustomerExistsException;
import com.restaurant.management.exception.customer.CustomerMessages;
import com.restaurant.management.exception.customer.CustomerNotFoundException;
import com.restaurant.management.exception.product.ProductExsitsException;
import com.restaurant.management.exception.product.ProductMessages;
import com.restaurant.management.exception.product.ProductNotFoundException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.*;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.CartService;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartOrderedRepository cartOrderedRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private LineItemRepository lineItemRepository;
    private AccountUserRepository accountUserRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           CartOrderedRepository cartOrderedRepository,
                           ProductRepository productRepository,
                           CustomerRepository customerRepository,
                           LineItemRepository lineItemRepository,
                           AccountUserRepository accountUserRepository) {
        this.cartRepository = cartRepository;
        this.cartOrderedRepository = cartOrderedRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.lineItemRepository = lineItemRepository;
        this.accountUserRepository = accountUserRepository;
    }

    public Optional<Cart> getCart(@CurrentUser UserPrincipal currentUser, Long customerId) {
        AccountUser accountUser = getUserById(currentUser);
        Long restaurantId = accountUser.getCompany().getId();

        return cartRepository.findCartByCustomerIdAndCompanyId(customerId, restaurantId);
    }

    public Cart getCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long customerId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return cartRepository.findCartByCustomerIdAndCompanyId(customerId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CUSTOMER_SESSION_CART_NOT_FOUND.getMessage()));
    }

    public Cart openCart(@CurrentUser UserPrincipal currentUser, Long customerId) {
        Customer customer = getCustomerByIdAndRestaurantId(currentUser, customerId);

        checkSessionCartStatus(customerId);

        Company company = getUserById(currentUser).getCompany();

        Cart newCart = new Cart.CartBuilder()
                .setIsOpen(Boolean.TRUE)
                .setCustomer(customer)
                .setCompany(company)
                .setTotalPrice(0.0)
                .build();

        newCart.setLineItems(new ArrayList<>());

        cartRepository.save(newCart);

        return newCart;
    }

    public Cart addToCart(@CurrentUser UserPrincipal currentUser,
                          Long customerId, UpdateCartRequest request) {
        validateCustomer(currentUser, customerId);

        Product product = getProductById(currentUser, request.getProductId());

        Cart newCart = getCartByCustomerId(currentUser, customerId);

        Optional<LineItem> sessionLineItem = newCart.getLineItems().stream()
                .filter(p -> p.getProduct().getId().equals(request.getProductId()))
                .findFirst();

        if (sessionLineItem.isPresent()) {
            throw new ProductExsitsException(ProductMessages.PROCUCT_ALREADY_IN_CART.getMessage());
        }

        double price = product.getPrice() * request.getQuantity();
        price = Math.floor(price * 100) / 100;

        sessionLineItem = Optional.of(new LineItem());
        newCart.getLineItems().add(sessionLineItem.get());

        sessionLineItem.get().setCompany(getUserById(currentUser).getCompany());
        sessionLineItem.get().setProduct(product);
        sessionLineItem.get().setQuantity(request.getQuantity());
        sessionLineItem.get().setPrice(price);

        newCart.setTotalPrice(newCart.calculateTotalPriceOf(newCart));

        cartRepository.save(newCart);

        return newCart;
    }

    public Cart updateProductQuantity(@CurrentUser UserPrincipal currentUser, Long customerId, UpdateCartRequest request) {

        validateCustomer(currentUser, customerId);

        Cart cart = getOpenSessionCartByCustomerId(customerId);

        LineItem lineItem = cart.getLineItems().stream()
                .filter(v -> v.getProduct().getId().equals(request.getProductId()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage() + request.getProductId()));

        if (request.getQuantity() < 0) {
            throw new ArithmeticException(CartMessages.NOT_ENOUGH_AT_CART.getMessage());
        }

        lineItem.setQuantity(request.getQuantity());
        lineItem.setPrice(lineItem.getProduct().getPrice() * request.getQuantity());
        cart.setTotalPrice(cart.calculateTotalPriceOf(cart));

        if (request.getQuantity() == 0) {
            cart.getLineItems().remove(lineItem);
        }

        cartRepository.save(cart);

        lineItemRepository.deleteById(lineItem.getId());

        return cart;
    }

    public Cart removeProductFromCart(@CurrentUser UserPrincipal currentUser, Long customerId, RemoveProductRequest request) {
        validateCustomer(currentUser, customerId);

        Cart cart = getOpenSessionCartByCustomerId(customerId);

        LineItem lineItem = cart.getLineItems().stream()
                .filter(v -> v.getProduct().getId().equals(request.getProductId()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage() + request.getProductId()));

        cart.getLineItems().remove(lineItem);

        cart.setTotalPrice(cart.calculateTotalPriceOf(cart));

        deleteLineItem(lineItem.getId());

        return cart;
    }

    public Page<Cart> getCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return cartRepository.findAllByCompanyId(restaurantId, pageable);
    }

    public Cart getCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return cartRepository.findByIdAndCompanyId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage() + cartId));
    }

    public ApiResponse deleteCart(@CurrentUser UserPrincipal currentUser, Long cartId) {

        Cart cart = getCartById(currentUser, cartId);

        cartRepository.deleteById(cart.getId());

        return new ApiResponse(true, CartMessages.CART_DELETED.getMessage());
    }

    public void deleteCart(Long cartId) {
        CartOrdered cartOrdered = cartOrderedRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage() + cartId));

        cartOrderedRepository.deleteById(cartOrdered.getId());
    }

    public ApiResponse deleteLineItem(Long id) {
        LineItem lineItem = lineItemRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage() + id));

        lineItemRepository.deleteById(lineItem.getId());

        return new ApiResponse(true, CartMessages.LINE_ITEM_DELETED.getMessage());
    }

    private AccountUser getUserById(@CurrentUser UserPrincipal currentUser) {
        return accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));
    }

    private Customer getCustomerByIdAndRestaurantId(@CurrentUser UserPrincipal currentUser, Long customerId) {
        AccountUser accountUser = getUserById(currentUser);

        return customerRepository.findByIdAndCompanyId(customerId, accountUser.getCompany().getId())
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage()));
    }

    private boolean checkSessionCartStatus(Long customerId) {
        if (cartRepository.existsByCustomerIdAndIsOpenTrue(customerId)) {
            throw new CartExistsException(CartMessages.CART_EXISTS.getMessage());
        }
        return true;
    }

    private boolean validateCustomer(@CurrentUser UserPrincipal currentUser, Long customerId) {
        Long restaurantId = getUserById(currentUser).getCompany().getId();

        if (!customerRepository.existsByIdAndCompanyId(customerId, restaurantId)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }
        return true;
    }

    private Product getProductById(@CurrentUser UserPrincipal currentUser, Long productId) {
        Long restaurantId = getUserById(currentUser).getCompany().getId();

        return productRepository.findByIdAndCompanyId(productId, restaurantId)
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage()));
    }

    private Cart getOpenSessionCartByCustomerId(Long customerId) {
        return cartRepository.findCartByCustomerIdAndIsOpenTrue(customerId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));
    }
}
