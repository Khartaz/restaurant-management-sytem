package com.restaurant.management.service;

import com.restaurant.management.domain.*;
import com.restaurant.management.exception.customer.CustomerMessages;
import com.restaurant.management.exception.customer.CustomerNotFoundException;
import com.restaurant.management.exception.product.ProductMessages;
import com.restaurant.management.exception.product.ProductNotFoundException;
import com.restaurant.management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public void addToCart(Long phoneNumber, String productName, Integer quantity) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.CUSTOMER_NOT_REGISTER.getErrorMessage()));

        Product product = productRepository.findProductByName(productName)
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_NOT_FOUND.getErrorMessage()));

        Optional<Cart> cart = cartRepository.findCartByCustomerPhoneNumberAndIsOpenTrue(phoneNumber);

        Cart newCart = new Cart();

        if (cart.isPresent()) {
            newCart = cart.get();
        }

        LineItem lineItem = new LineItem();

        lineItem.setProduct(product);
        lineItem.setQuantity(quantity);
        lineItem.setPrice(product.getPrice());

        newCart.setOpen(true);
        newCart.setCustomer(customer);
        newCart.getLineItems().add(lineItem);

        cartRepository.save(newCart);
    }

}
