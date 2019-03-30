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
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.repository.*;
import com.restaurant.management.repository.archive.CustomerArchiveRepository;
import com.restaurant.management.repository.archive.ProductArchiveRepository;
import com.restaurant.management.utils.Utils;
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

    @Autowired
    public CartService(SessionCartRepository sessionCartRepository,
                       CartRepository cartRepository,
                       ProductRepository productRepository,
                       CustomerRepository customerRepository,
                       CartMapper cartMapper,
                       SessionLineItemRepository sessionLineItemRepository,
                       ProductArchiveRepository productArchiveRepository,
                       CustomerArchiveRepository customerArchiveRepository) {
        this.sessionCartRepository = sessionCartRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.cartMapper = cartMapper;
        this.sessionLineItemRepository = sessionLineItemRepository;
        this.productArchiveRepository = productArchiveRepository;
        this.customerArchiveRepository = customerArchiveRepository;
    }

    public SessionCart openSessionCart(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage()));

        if (sessionCartRepository.existsByCustomerAndIsOpenTrue(customer)) {
            throw new CartExistsException(CartMessages.CART_EXISTS.getMessage());
        }

        SessionCart newSessionCart = new SessionCart();

        newSessionCart.setUniqueId(Utils.generateCartUniqueId(5));
        newSessionCart.setOpen(Boolean.TRUE);
        newSessionCart.setCustomer(customer);

        sessionCartRepository.save(newSessionCart);

        return newSessionCart;
    }

    public SessionCart addToCart(Long id, UpdateCartRequest request) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }

        Product product = productRepository.findProductByName(request.getProductName())
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_NAME_NOT_FOUND.getMessage()));

        SessionCart newSessionCart = sessionCartRepository.findSessionCartByCustomerIdAndIsOpenTrue(id)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        Optional<SessionLineItem> lineItem = newSessionCart.getSessionLineItems().stream()
                .filter(p -> p.getProduct().getName().equals(request.getProductName()))
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

            lineItem.get().setProduct(product);
            lineItem.get().setQuantity(request.getQuantity());
            lineItem.get().setPrice(price);
        }

        sessionCartRepository.save(newSessionCart);

        return newSessionCart;
    }

    public SessionCart updateProductQuantity(Long id, UpdateCartRequest request) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }

        SessionCart sessionCart = sessionCartRepository.findSessionCartByCustomerIdAndIsOpenTrue(id)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        SessionLineItem sessionLineItem = sessionCart.getSessionLineItems().stream()
                .filter(v -> v.getProduct().getName().equals(request.getProductName()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_NAME_NOT_FOUND.getMessage() + request.getProductName()));

        if (request.getQuantity() < 0) {
            throw new ArithmeticException(CartMessages.NOT_ENOUGH_AT_CART.getMessage());
        }

        sessionLineItem.setQuantity(request.getQuantity());
        sessionLineItem.setPrice(sessionLineItem.getProduct().getPrice() * request.getQuantity());

        sessionCartRepository.save(sessionCart);

        return sessionCart;
    }

    public SessionCart removeProductFromCart(Long id, RemoveProductRequest request) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }

        SessionCart sessionCart = sessionCartRepository.findSessionCartByCustomerIdAndIsOpenTrue(id)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        SessionLineItem sessionLineItem = sessionCart.getSessionLineItems().stream()
                .filter(v -> v.getProduct().getName().equals(request.getProductName()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_NAME_NOT_FOUND.getMessage() + request.getProductName()));

        sessionCart.getSessionLineItems().remove(sessionLineItem);
        deleteLineItem(sessionLineItem.getId());

        return sessionCart;
    }

    public Cart confirmCart(Long id) {
        SessionCart sessionCart = sessionCartRepository.findSessionCartByCustomerId(id)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        sessionCart.setOpen(Boolean.FALSE);

        Cart cart = cartMapper.mapToCart(sessionCart);

        CustomerArchive customerArchive = cart.getCustomer();

        customerArchiveRepository.save(customerArchive);

        List<ProductArchive> productArchives = cart.getLineItems().stream()
                .map(LineItemArchive::getProduct)
                .collect(Collectors.toList());

        productArchiveRepository.saveAll(productArchives);

        cartRepository.save(cart);

        deleteSessionCart(sessionCart.getUniqueId());

        return cart;
    }

    public Page<Cart> getAllCarts(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }

    public Page<Cart> getCustomerCarts(Long id, Pageable pageable) {
        return cartRepository.findByCustomerId(id, pageable);
    }

    public Cart getCustomerCartByUniqueId(Long id, String uniqueId) {
        Optional<Cart> cart = cartRepository.findByCustomerIdAndUniqueId(id, uniqueId);

        if (!cart.isPresent()) {
            throw new CartNotFoundException(CartMessages.CUSTOMER_CART_NOT_FOUND.getMessage());
        }
        return cart.get();
    }

    public Page<SessionCart> getSessionCarts(Pageable pageable) {
       return sessionCartRepository.findAll(pageable);
    }

    public Cart getCartByUniqueId(String uniqueId) {
        Optional<Cart> cart = cartRepository.findByUniqueId(uniqueId);

        if (!cart.isPresent()) {
            throw new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage());
        }
        return cart.get();
    }

    public SessionCart getSessionCartByUniqueId(String uniqueId) {
        Optional<SessionCart> sessionCart = sessionCartRepository.findByUniqueId(uniqueId);

        if (!sessionCart.isPresent()) {
            throw new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + uniqueId);
        }
        return sessionCart.get();
    }

    public SessionCart getSessionCartByCustomerId(Long id) {
        Optional<SessionCart> sessionCart = sessionCartRepository.findSessionCartByCustomerId(id);

        if (!sessionCart.isPresent()) {
            throw new CartNotFoundException(CartMessages.CUSTOMER_SESSION_CART_NOT_FOUND.getMessage());
        }
        return sessionCart.get();
    }

    public ApiResponse deleteSessionCart(String uniqueId) {
        Optional<SessionCart> sessionCart = sessionCartRepository.findByUniqueId(uniqueId);

        if (sessionCart.isPresent()) {
            sessionCartRepository.deleteByUniqueId(sessionCart.get().getUniqueId());

            return new ApiResponse(true, CartMessages.CART_DELETED.getMessage());
        }

        throw new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + uniqueId);
    }

    private void deleteCart(String uniqueId) {
        Optional<Cart> cart = cartRepository.findByUniqueId(uniqueId);

        if (cart.isPresent()) {
            cartRepository.deleteById(cart.get().getId());
        } else {
            throw new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + uniqueId);
        }
    }

    public ApiResponse deleteLineItem(Long id) {
        SessionLineItem lineItem = sessionLineItemRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + id));

        sessionLineItemRepository.deleteById(lineItem.getId());

        return new ApiResponse(true, CartMessages.LINE_ITEM_DELETED.getMessage());
    }
}
