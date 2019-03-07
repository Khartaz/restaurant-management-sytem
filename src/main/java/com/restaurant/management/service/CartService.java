package com.restaurant.management.service;

import com.restaurant.management.domain.*;
import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.exception.cart.CartExistsException;
import com.restaurant.management.exception.cart.CartMessages;
import com.restaurant.management.exception.cart.CartNotFoundException;
import com.restaurant.management.exception.customer.CustomerMessages;
import com.restaurant.management.exception.customer.CustomerNotFoundException;
import com.restaurant.management.exception.product.ProductMessages;
import com.restaurant.management.exception.product.ProductNotFoundException;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.repository.*;
import com.restaurant.management.utils.Utils;
import com.restaurant.management.web.request.cart.RegisterCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private CartMapper cartMapper;
    private Utils utils;

    @Autowired
    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       CustomerRepository customerRepository,
                       CartMapper cartMapper,
                       Utils utils) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.cartMapper = cartMapper;
        this.utils = utils;
    }

    public CartDto registerCustomerCart(RegisterCartRequest registerCartRequest) {
        Customer customer = customerRepository.findByPhoneNumber(registerCartRequest.getPhoneNumber())
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage()));

        if (cartRepository.existsByCustomerAndIsOpenTrue(customer)) {
            throw new CartExistsException(CartMessages.CART_EXISTS.getMessage());
        }

        Cart newCart = new Cart();

        newCart.setUniqueId(utils.generateCartUniqueId(5));
        newCart.setOpen(true);
        newCart.setCustomer(customer);

        cartRepository.save(newCart);

        return cartMapper.mapToCartDto(newCart);
    }

    public CartDto addToCart(Long phoneNumber, String productName, Integer quantity) {
//        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
//                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage()));

        Product product = productRepository.findProductByName(productName)
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_NOT_FOUND.getMessage()));

        Optional<Cart> cart = cartRepository.findCartByCustomerPhoneNumberAndIsOpenTrue(phoneNumber);

        Cart newCart = new Cart();

        if (cart.isPresent()) {
            newCart = cart.get();
        }

        LineItem lineItem = new LineItem();

        lineItem.setProduct(product);
        lineItem.setQuantity(quantity);
        lineItem.setPrice(product.getPrice());

        if (!cart.isPresent()) {
            newCart.setUniqueId(utils.generateCartUniqueId(5));
        }

//        newCart.setOpen(true);
//        newCart.setCustomer(customer);
        newCart.getLineItems().add(lineItem);

        cartRepository.save(newCart);

        return cartMapper.mapToCartDto(newCart);
    }

    public List<CartDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();

        return cartMapper.mapToCartDtoList(carts);
    }

    public List<CartDto> getOpenedCarts() {
        List<Cart> carts = cartRepository.findAllByIsOpenIsTrue()
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_OPENED_EMPTY.getMessage()));

        return cartMapper.mapToCartDtoList(carts);
    }

    public List<CartDto> getClosedCarts() {
        List<Cart> carts = cartRepository.findAllByIsOpenIsFalse()
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_CLOSED_EMPTY.getMessage()));

        return cartMapper.mapToCartDtoList(carts);
    }

    public CartDto getCartByUniqueId(String uniqueId) {
        Optional<Cart> cart = cartRepository.findByUniqueId(uniqueId);

        if (!cart.isPresent()) {
            throw new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + uniqueId);
        }
        return cartMapper.mapToCartDto(cart.get());
    }

    public boolean deleteCart(String uniqueId) {
        Optional<Cart> cart = cartRepository.findByUniqueId(uniqueId);

        if (cart.isPresent()) {
            cartRepository.deleteById(cart.get().getId());
            return true;
        } else {
            throw new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + uniqueId);
        }
    }
}
