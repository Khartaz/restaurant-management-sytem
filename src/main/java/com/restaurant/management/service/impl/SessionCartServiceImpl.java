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
import com.restaurant.management.service.SessionCartService;
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
public class SessionCartServiceImpl implements SessionCartService {

    private SessionCartRepository sessionCartRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private SessionLineItemRepository sessionLineItemRepository;
    private AccountUserRepository accountUserRepository;

    @Autowired
    public SessionCartServiceImpl(SessionCartRepository sessionCartRepository,
                                  CartRepository cartRepository,
                                  ProductRepository productRepository,
                                  CustomerRepository customerRepository,
                                  SessionLineItemRepository sessionLineItemRepository,
                                  AccountUserRepository accountUserRepository) {
        this.sessionCartRepository = sessionCartRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.sessionLineItemRepository = sessionLineItemRepository;
        this.accountUserRepository = accountUserRepository;
    }

    public Optional<SessionCart> getSessionCart(@CurrentUser UserPrincipal currentUser, Long customerId) {
        AccountUser accountUser = getUserById(currentUser);
        Long restaurantId = accountUser.getCompany().getId();

        return sessionCartRepository.findSessionCartByCustomerIdAndCompanyId(customerId, restaurantId);
    }

    public SessionCart getSessionCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long customerId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return sessionCartRepository.findSessionCartByCustomerIdAndCompanyId(customerId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CUSTOMER_SESSION_CART_NOT_FOUND.getMessage()));
    }

    public SessionCart openSessionCart(@CurrentUser UserPrincipal currentUser,  Long customerId) {
        Customer customer = getCustomerByIdAndRestaurantId(currentUser, customerId);

        checkSessionCartStatus(customerId);

        Company company = getUserById(currentUser).getCompany();

        SessionCart newSessionCart = new SessionCart.SessionCartBuilder()
                .setIsOpen(Boolean.TRUE)
                .setCustomer(customer)
                .setCompany(company)
                .setTotalPrice(0.0)
                .build();

        newSessionCart.setSessionLineItems(new ArrayList<>());

        sessionCartRepository.save(newSessionCart);

        return newSessionCart;
    }

    public SessionCart addToCart(@CurrentUser UserPrincipal currentUser,
                                 Long customerId, UpdateCartRequest request) {
        validateCustomer(currentUser, customerId);

        Product product = getProductById(currentUser, request.getProductId());

        SessionCart newSessionCart = getSessionCartByCustomerId(currentUser, customerId);

        Optional<SessionLineItem> sessionLineItem = newSessionCart.getSessionLineItems().stream()
                .filter(p -> p.getProduct().getId().equals(request.getProductId()))
                .findFirst();

        if (sessionLineItem.isPresent()) {
            throw new ProductExsitsException(ProductMessages.PROCUCT_ALREADY_IN_CART.getMessage());
        }

        double price = product.getPrice() * request.getQuantity();
        price = Math.floor(price * 100) / 100;

        sessionLineItem = Optional.of(new SessionLineItem());
        newSessionCart.getSessionLineItems().add(sessionLineItem.get());

        sessionLineItem.get().setCompany(getUserById(currentUser).getCompany());
        sessionLineItem.get().setProduct(product);
        sessionLineItem.get().setQuantity(request.getQuantity());
        sessionLineItem.get().setPrice(price);

        newSessionCart.setTotalPrice(newSessionCart.calculateTotalPriceOf(newSessionCart));

        sessionCartRepository.save(newSessionCart);

        return newSessionCart;
    }

    public SessionCart updateProductQuantity(@CurrentUser UserPrincipal currentUser, Long customerId, UpdateCartRequest request) {

        validateCustomer(currentUser, customerId);

        SessionCart sessionCart = getOpenSessionCartByCustomerId(customerId);

        SessionLineItem sessionLineItem = sessionCart.getSessionLineItems().stream()
                .filter(v -> v.getProduct().getId().equals(request.getProductId()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage() + request.getProductId()));

        if (request.getQuantity() < 0) {
            throw new ArithmeticException(CartMessages.NOT_ENOUGH_AT_CART.getMessage());
        }

        sessionLineItem.setQuantity(request.getQuantity());
        sessionLineItem.setPrice(sessionLineItem.getProduct().getPrice() * request.getQuantity());
        sessionCart.setTotalPrice(sessionCart.calculateTotalPriceOf(sessionCart));

        if (request.getQuantity() == 0) {
            sessionCart.getSessionLineItems().remove(sessionLineItem);
        }

        sessionCartRepository.save(sessionCart);

        sessionLineItemRepository.deleteById(sessionLineItem.getId());

        return sessionCart;
    }

    public SessionCart removeProductFromCart(@CurrentUser UserPrincipal currentUser, Long customerId, RemoveProductRequest request) {
        validateCustomer(currentUser, customerId);

        SessionCart sessionCart = getOpenSessionCartByCustomerId(customerId);

        SessionLineItem sessionLineItem = sessionCart.getSessionLineItems().stream()
                .filter(v -> v.getProduct().getId().equals(request.getProductId()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage() + request.getProductId()));

        sessionCart.getSessionLineItems().remove(sessionLineItem);

        sessionCart.setTotalPrice(sessionCart.calculateTotalPriceOf(sessionCart));

        deleteLineItem(sessionLineItem.getId());

        return sessionCart;
    }

    public Page<SessionCart> getSessionCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return sessionCartRepository.findAllByCompanyId(restaurantId, pageable);
    }

    public SessionCart getSessionCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return sessionCartRepository.findByIdAndCompanyId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage() + cartId));
    }

    public ApiResponse deleteSessionCart(@CurrentUser UserPrincipal currentUser, Long cartId) {

        SessionCart sessionCart = getSessionCartById(currentUser, cartId);

        sessionCartRepository.deleteById(sessionCart.getId());

        return new ApiResponse(true, CartMessages.CART_DELETED.getMessage());
    }

    public void deleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage() + cartId));

        cartRepository.deleteById(cart.getId());
    }

    public ApiResponse deleteLineItem(Long id) {
        SessionLineItem lineItem = sessionLineItemRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_ID_NOT_FOUND.getMessage() + id));

        sessionLineItemRepository.deleteById(lineItem.getId());

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
        if (sessionCartRepository.existsByCustomerIdAndIsOpenTrue(customerId)) {
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

    private SessionCart getOpenSessionCartByCustomerId(Long customerId) {
        return sessionCartRepository.findSessionCartByCustomerIdAndIsOpenTrue(customerId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));
    }
}
