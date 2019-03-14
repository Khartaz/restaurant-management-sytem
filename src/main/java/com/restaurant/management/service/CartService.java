package com.restaurant.management.service;

import com.restaurant.management.domain.*;
import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.exception.cart.CartException;
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
import com.restaurant.management.utils.Utils;
import com.restaurant.management.web.request.cart.RegisterCartRequest;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class SessionCartService {

    private SessionCartRepository sessionCartRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private CartMapper cartMapper;
    private Utils utils;
    private LineItemRepository lineItemRepository;

    @Autowired
    public SessionCartService(SessionCartRepository sessionCartRepository,
                              ProductRepository productRepository,
                              CustomerRepository customerRepository,
                              CartMapper cartMapper,
                              Utils utils,
                              LineItemRepository lineItemRepository) {
        this.sessionCartRepository = sessionCartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.cartMapper = cartMapper;
        this.utils = utils;
        this.lineItemRepository = lineItemRepository;
    }

    public CartDto openSessionCart(RegisterCartRequest request) {
        Customer customer = customerRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage()));

        if (sessionCartRepository.existsByCustomerAndIsOpenTrue(customer)) {
            throw new CartExistsException(CartMessages.CART_EXISTS.getMessage());
        }

        SessionCart newSessionCart = new SessionCart();

        newSessionCart.setUniqueId(utils.generateCartUniqueId(5));
        newSessionCart.setOpen(true);
        newSessionCart.setCustomer(customer);

        sessionCartRepository.save(newSessionCart);

        return cartMapper.mapToCartDto(newSessionCart);
    }

    public CartDto addToCart(UpdateCartRequest request) {
        if (!customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }

        Product product = productRepository.findProductByName(request.getProductName())
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage()));

        SessionCart newSessionCart = sessionCartRepository.findSessionCartByCustomerPhoneNumberAndIsOpenTrue(request.getPhoneNumber())
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        Optional<SessionLineItem> lineItem = newSessionCart.getSessionLineItems().stream()
                .filter(p -> p.getProduct().getName().equals(request.getProductName()))
                .findFirst();

        if (!lineItem.isPresent()) {
            lineItem = Optional.of(new SessionLineItem());
            newSessionCart.getSessionLineItems().add(lineItem.get());
        }

        lineItem.get().setProduct(product);
        lineItem.get().setQuantity(request.getQuantity());
        lineItem.get().setPrice(product.getPrice() * request.getQuantity());

        sessionCartRepository.save(newSessionCart);

        return cartMapper.mapToCartDto(newSessionCart);
    }

    public CartDto updateProductQuantity(UpdateCartRequest request) {
        if (!customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }

        SessionCart sessionCart = sessionCartRepository.findSessionCartByCustomerPhoneNumberAndIsOpenTrue(request.getPhoneNumber())
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

        return cartMapper.mapToCartDto(sessionCart);
    }

    public CartDto deleteProductFromCart(RemoveProductRequest request) {
        if (!customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }

        SessionCart sessionCart = sessionCartRepository.findSessionCartByCustomerPhoneNumberAndIsOpenTrue(request.getPhoneNumber())
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        SessionLineItem sessionLineItem = sessionCart.getSessionLineItems().stream()
                .filter(v -> v.getProduct().getName().equals(request.getProductName()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_NAME_NOT_FOUND.getMessage() + request.getProductName()));

        sessionCart.getSessionLineItems().remove(sessionLineItem);
        deleteLineItem(sessionLineItem.getId());

        return cartMapper.mapToCartDto(sessionCart);
    }

    public CartDto checkoutCart(Long phoneNumber) {
        SessionCart sessionCart = sessionCartRepository.findSessionCartByCustomerPhoneNumberAndIsOpenTrue(phoneNumber)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

         return cartMapper.mapToCartDto(sessionCart);
    }

    public List<CartDto> getAllCarts() {
        List<SessionCart> sessionCarts = sessionCartRepository.findAll();

        return cartMapper.mapToCartDtoList(sessionCarts);
    }

    public List<CartDto> getOpenedCarts() {
        List<SessionCart> sessionCarts = sessionCartRepository.findAllByIsOpenIsTrue()
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_OPENED_EMPTY.getMessage()));

        return cartMapper.mapToCartDtoList(sessionCarts);
    }

//    public List<CartDto> getClosedCarts() {
//        List<SessionCart> sessionCarts = sessionCartRepository.findAllByIsOpenIsFalse()
//                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_CLOSED_EMPTY.getMessage()));
//
//        return cartMapper.mapToCartDtoList(sessionCarts);
//    }

    public CartDto getCartByUniqueId(String uniqueId) {
        Optional<SessionCart> cart = sessionCartRepository.findByUniqueId(uniqueId);

        if (!cart.isPresent()) {
            throw new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + uniqueId);
        }
        return cartMapper.mapToCartDto(cart.get());
    }

    public void deleteCart(String uniqueId) {
        Optional<SessionCart> cart = sessionCartRepository.findByUniqueId(uniqueId);

        if (cart.isPresent()) {
            sessionCartRepository.deleteById(cart.get().getId());
        } else {
            throw new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + uniqueId);
        }

        if (!cart.get().getOpen()) {
            throw new CartException(CartMessages.CART_IS_CLOSED.getMessage());
        }
    }

    private void deleteLineItem(Long id) {
        Optional<SessionLineItem> lineItem = lineItemRepository.findById(id);

        if (lineItem.isPresent()) {
            lineItemRepository.deleteById(lineItem.get().getId());
        } else {
            throw new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + id);
        }
    }
}
