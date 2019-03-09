package com.restaurant.management.service;

import com.restaurant.management.domain.*;
import com.restaurant.management.domain.dto.CartDto;
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
import com.restaurant.management.web.request.order.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class CartService {

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private CartMapper cartMapper;
    private Utils utils;
    private LineItemRepository lineItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       CustomerRepository customerRepository,
                       CartMapper cartMapper,
                       Utils utils,
                       LineItemRepository lineItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.cartMapper = cartMapper;
        this.utils = utils;
        this.lineItemRepository = lineItemRepository;
    }

    public CartDto openCustomerCart(RegisterCartRequest request) {
        Customer customer = customerRepository.findByPhoneNumber(request.getPhoneNumber())
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

    public CartDto addToCart(OrderRequest request) {
        if (!customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }

        Product product = productRepository.findProductByName(request.getProductName())
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage()));

        Cart newCart = cartRepository.findCartByCustomerPhoneNumberAndIsOpenTrue(request.getPhoneNumber())
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        Optional<LineItem> lineItem = newCart.getLineItems().stream()
                .filter(p -> p.getProduct().getName().equals(request.getProductName()))
                .findFirst();

        if (!lineItem.isPresent()) {
            lineItem = Optional.of(new LineItem());
            newCart.getLineItems().add(lineItem.get());
        }

        lineItem.get().setProduct(product);
        lineItem.get().setQuantity(request.getQuantity());
        lineItem.get().setPrice(product.getPrice() * request.getQuantity());

        cartRepository.save(newCart);

        return cartMapper.mapToCartDto(newCart);
    }

    public CartDto updateProductQuantity(OrderRequest request) {
        if (!customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }

        Cart cart = cartRepository.findCartByCustomerPhoneNumberAndIsOpenTrue(request.getPhoneNumber())
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        LineItem lineItem = cart.getLineItems().stream()
                .filter(v -> v.getProduct().getName().equals(request.getProductName()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_NAME_NOT_FOUND.getMessage() + request.getProductName()));

        if (request.getQuantity() < 0) {
            throw new ArithmeticException(CartMessages.NOT_ENOUGH_AT_CART.getMessage());
        }

        lineItem.setQuantity(request.getQuantity());
        lineItem.setPrice(lineItem.getProduct().getPrice() * request.getQuantity());

        cartRepository.save(cart);

        return cartMapper.mapToCartDto(cart);
    }

    public CartDto deleteProductFromCart(OrderRequest request) {
        if (!customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_NOT_REGISTER.getMessage());
        }

        Cart cart = cartRepository.findCartByCustomerPhoneNumberAndIsOpenTrue(request.getPhoneNumber())
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

        LineItem lineItem = cart.getLineItems().stream()
                .filter(v -> v.getProduct().getName().equals(request.getProductName()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_NAME_NOT_FOUND.getMessage() + request.getProductName()));

        cart.getLineItems().remove(lineItem);
        deleteLineItem(lineItem.getId());

        return cartMapper.mapToCartDto(cart);
    }

    public CartDto checkoutCart(Long phoneNumber) {
        Cart cart = cartRepository.findCartByCustomerPhoneNumberAndIsOpenTrue(phoneNumber)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_REGISTER.getMessage()));

         return cartMapper.mapToCartDto(cart);
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

    public void deleteCart(String uniqueId) {
        Optional<Cart> cart = cartRepository.findByUniqueId(uniqueId);

        if (cart.isPresent()) {
            cartRepository.deleteById(cart.get().getId());
        } else {
            throw new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + uniqueId);
        }
    }

    private void deleteLineItem(Long id) {
        Optional<LineItem> lineItem = lineItemRepository.findById(id);

        if (lineItem.isPresent()) {
            lineItemRepository.deleteById(lineItem.get().getId());
        } else {
            throw new CartNotFoundException(CartMessages.CART_UNIQUE_ID_NOT_FOUND.getMessage() + id);
        }
    }
}
