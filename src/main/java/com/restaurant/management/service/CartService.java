package com.restaurant.management.service;

import com.restaurant.management.domain.*;
import com.restaurant.management.domain.archive.CustomerArchive;
import com.restaurant.management.domain.archive.LineItemArchive;
import com.restaurant.management.domain.archive.ProductArchive;
import com.restaurant.management.exception.cart.CartExistsException;
import com.restaurant.management.exception.cart.CartMessages;
import com.restaurant.management.exception.cart.CartNotFoundException;
import com.restaurant.management.exception.customer.CustomerExistsException;
import com.restaurant.management.exception.customer.CustomerMessages;
import com.restaurant.management.exception.customer.CustomerNotFoundException;
import com.restaurant.management.exception.product.ProductMessages;
import com.restaurant.management.exception.product.ProductNotFoundException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.repository.*;
import com.restaurant.management.repository.archive.CustomerArchiveRepository;
import com.restaurant.management.repository.archive.ProductArchiveRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class CartService {

    private SessionCartRepository sessionCartRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private CartMapper cartMapper;
    private SessionLineItemRepository sessionLineItemRepository;
    private ProductArchiveRepository productArchiveRepository;
    private CustomerArchiveRepository customerArchiveRepository;
    private AccountUserRepository accountUserRepository;

    @Autowired
    public CartService(SessionCartRepository sessionCartRepository,
                       CartRepository cartRepository,
                       ProductRepository productRepository,
                       CustomerRepository customerRepository,
                       CartMapper cartMapper,
                       SessionLineItemRepository sessionLineItemRepository,
                       ProductArchiveRepository productArchiveRepository,
                       CustomerArchiveRepository customerArchiveRepository,
                       AccountUserRepository accountUserRepository) {
        this.sessionCartRepository = sessionCartRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.cartMapper = cartMapper;
        this.sessionLineItemRepository = sessionLineItemRepository;
        this.productArchiveRepository = productArchiveRepository;
        this.customerArchiveRepository = customerArchiveRepository;
        this.accountUserRepository = accountUserRepository;
    }

    public SessionCart openSessionCart(@CurrentUser UserPrincipal currentUser,  Long id) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        Customer customer = customerRepository.findByIdAndRestaurantInfoId(id, restaurantId)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage()));

        if (sessionCartRepository.existsByCustomerAndIsOpenTrue(customer)) {
            throw new CartExistsException(CartMessages.CART_EXISTS.getMessage());
        }

        SessionCart newSessionCart = new SessionCart();

        newSessionCart.setOpen(Boolean.TRUE);
        newSessionCart.setCustomer(customer);
        newSessionCart.setRestaurantInfo(accountUser.getRestaurantInfo());

        sessionCartRepository.save(newSessionCart);

        return newSessionCart;
    }

    public SessionCart addToCart(@CurrentUser UserPrincipal currentUser, Long customerId, UpdateCartRequest request) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        if (!customerRepository.existsByIdAndRestaurantInfoId(customerId, restaurantId)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }

        Product product = productRepository.findByIdAndRestaurantInfoId(request.getProductId(), restaurantId)
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage()));

        SessionCart newSessionCart = sessionCartRepository.findSessionCartByCustomerIdAndRestaurantInfoId(customerId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        Optional<SessionLineItem> lineItem = newSessionCart.getSessionLineItems().stream()
                .filter(p -> p.getProduct().getId().equals(request.getProductId()))
                .findFirst();

        lineItem.ifPresent(item -> {
            Integer quantity = item.getQuantity() + request.getQuantity();

            double price = item.getProduct().getPrice() * quantity;
            price = Math.floor(price * 100) / 100;

            item.setQuantity(quantity);
            item.setPrice(price);
        });

        if (!lineItem.isPresent()) {
            double price = product.getPrice() * request.getQuantity();
            price = Math.floor(price * 100) / 100;

            lineItem = Optional.of(new SessionLineItem());
            newSessionCart.getSessionLineItems().add(lineItem.get());

            lineItem.get().setRestaurantInfo(accountUser.getRestaurantInfo());
            lineItem.get().setProduct(product);
            lineItem.get().setQuantity(request.getQuantity());
            lineItem.get().setPrice(price);
        }

        sessionCartRepository.save(newSessionCart);

        return newSessionCart;
    }

    public SessionCart updateProductQuantity(@CurrentUser UserPrincipal currentUser, Long id, UpdateCartRequest request) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        if (!customerRepository.existsByIdAndRestaurantInfoId(id, restaurantId)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }

        SessionCart sessionCart = sessionCartRepository.findSessionCartByCustomerIdAndIsOpenTrue(id)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        SessionLineItem sessionLineItem = sessionCart.getSessionLineItems().stream()
                .filter(v -> v.getProduct().getId().equals(request.getProductId()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage() + request.getProductId()));

        if (request.getQuantity() < 0) {
            throw new ArithmeticException(CartMessages.NOT_ENOUGH_AT_CART.getMessage());
        }

        sessionLineItem.setQuantity(request.getQuantity());
        sessionLineItem.setPrice(sessionLineItem.getProduct().getPrice() * request.getQuantity());

        sessionCartRepository.save(sessionCart);

        return sessionCart;
    }

    public SessionCart removeProductFromCart(@CurrentUser UserPrincipal currentUser, Long id, RemoveProductRequest request) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        if (!customerRepository.existsByIdAndRestaurantInfoId(id, restaurantId)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }

        SessionCart sessionCart = sessionCartRepository.findSessionCartByCustomerIdAndIsOpenTrue(id)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        SessionLineItem sessionLineItem = sessionCart.getSessionLineItems().stream()
                .filter(v -> v.getProduct().getId().equals(request.getProductId()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage() + request.getProductId()));

        sessionCart.getSessionLineItems().remove(sessionLineItem);
        deleteLineItem(sessionLineItem.getId());

        return sessionCart;
    }

    public Cart confirmCart(@CurrentUser UserPrincipal currentUser, Long customerId) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        SessionCart sessionCart = sessionCartRepository.findSessionCartByCustomerIdAndRestaurantInfoId(customerId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        sessionCart.setOpen(Boolean.FALSE);

        Cart cart = cartMapper.mapToCart(sessionCart);

        cart.setRestaurantInfo(accountUser.getRestaurantInfo()); /// TO FIX add to mapper
        cart.getLineItems().forEach(v -> v.setRestaurantInfo(accountUser.getRestaurantInfo()));

        CustomerArchive customerArchive = cart.getCustomer();
        customerArchive.setRestaurantInfo(accountUser.getRestaurantInfo());

        customerArchiveRepository.save(customerArchive);

        List<ProductArchive> productArchives = cart.getLineItems().stream()
                .map(LineItemArchive::getProduct)
                .collect(Collectors.toList());

        productArchives.forEach(v -> v.setRestaurantInfo(accountUser.getRestaurantInfo()));

        productArchiveRepository.saveAll(productArchives);

        cartRepository.save(cart);

        deleteSessionCart(currentUser, sessionCart.getId());

        return cart;
    }

    public Page<Cart> getAllCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return cartRepository.findAllByRestaurantInfoId(restaurantId, pageable);
    }

    public Page<Cart> getCustomerCarts(@CurrentUser UserPrincipal currentUser,
                                       Long id, Pageable pageable) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return cartRepository.findByCustomerIdAndRestaurantInfoId(id, restaurantId,  pageable);
    }

    public Cart getCustomerCartById(@CurrentUser UserPrincipal currentUser, Long customerId, Long cartId) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return cartRepository.findByIdAndRestaurantInfoIdAndCustomerId(cartId, customerId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CUSTOMER_CART_NOT_FOUND.getMessage()));
    }

    public Page<SessionCart> getSessionCarts(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

       return sessionCartRepository.findAllByRestaurantInfoId(restaurantId, pageable);
    }

    public Cart getCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return cartRepository.findByIdAndRestaurantInfoId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage()));
    }

    public SessionCart getSessionCartById(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return sessionCartRepository.findByIdAndRestaurantInfoId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + cartId));
    }

    public SessionCart getSessionCartByCustomerId(@CurrentUser UserPrincipal currentUser, Long id) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return sessionCartRepository.findSessionCartByCustomerIdAndRestaurantInfoId(id, restaurantId)
               .orElseThrow(() -> new CartNotFoundException(CartMessages.CUSTOMER_SESSION_CART_NOT_FOUND.getMessage()));
    }

    public ApiResponse deleteSessionCart(@CurrentUser UserPrincipal currentUser, Long cartId) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        SessionCart sessionCart = sessionCartRepository.findByIdAndRestaurantInfoId(cartId, restaurantId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + cartId));

        sessionCartRepository.deleteById(sessionCart.getId());

        return new ApiResponse(true, CartMessages.CART_DELETED.getMessage());
    }

    private void deleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + cartId));

        cartRepository.deleteById(cart.getId());
    }

    public ApiResponse deleteLineItem(Long id) {
        SessionLineItem lineItem = sessionLineItemRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + id));

        sessionLineItemRepository.deleteById(lineItem.getId());

        return new ApiResponse(true, CartMessages.LINE_ITEM_DELETED.getMessage());
    }
}
