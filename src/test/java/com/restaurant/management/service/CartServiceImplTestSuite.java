package com.restaurant.management.service;

import com.restaurant.management.domain.*;
import com.restaurant.management.domain.archive.CustomerArchive;
import com.restaurant.management.domain.archive.LineItemArchive;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.repository.*;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceImplTestSuite {
    @InjectMocks
    private CartService cartService;
    @Mock
    private SessionCartRepository sessionCartRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private SessionLineItemRepository sessionLineItemRepository;

    private static final String UNIQUE_CART_ID = "J4L2H";

    private static final long CUSTOMER_ID = 1L;
    private static final long PHONE_NUMBER = 684293190L;
    private static final String CUSTOMER_NAME = "Customer name";
    private static final String CUSTOMER_LASTNAME = "Customer lastname";
    private static final String CUSTOMER_EMAIL = "customer@email.com";

    private static final String PRODUCT_NAME = "Product name";
    private static final String PRODUCT_CATEGORY = "Product category";

    @Test
    public void shouldOpenSessionCart() {
        //GIVEN
        Optional<Customer> customer = Optional.of(new Customer(
                CUSTOMER_ID,
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                CUSTOMER_EMAIL,
                PHONE_NUMBER));

        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(customer);
        when(sessionCartRepository.existsByCustomerAndIsOpenTrue(customer.get())).thenReturn(Boolean.FALSE);
        //WHEN
        SessionCart result = cartService.openSessionCart(CUSTOMER_ID);
        //THEN
        assertAll(
                () -> assertTrue(result.isOpen()),
                () -> assertNotNull(result.getCustomer()),
                () -> assertEquals(result.getUniqueId().length(), 5)
        );
    }

    @Test
    public void shouldAddToCart() {
        //GIVEN
        UpdateCartRequest request = new UpdateCartRequest(
                PRODUCT_NAME,
                5);

        Product product = new Product();
        product.setName(request.getProductName());
        product.setCategory(PRODUCT_CATEGORY);
        product.setPrice(2.0);

        Optional<Customer> customer = Optional.of(new Customer(
                CUSTOMER_ID,
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                CUSTOMER_EMAIL,
                PHONE_NUMBER));

        SessionCart sessionCart = new SessionCart();

        sessionCart.setUniqueId(UNIQUE_CART_ID);
        sessionCart.setOpen(Boolean.TRUE);
        sessionCart.setCustomer(customer.get());

        when(customerRepository.existsById(CUSTOMER_ID)).thenReturn(Boolean.TRUE);
        when(productRepository.findProductByName(anyString())).thenReturn(Optional.of(product));
        when(sessionCartRepository.findSessionCartByCustomerIdAndIsOpenTrue(CUSTOMER_ID)).thenReturn(Optional.of(sessionCart));
        //WHEN
        SessionCart result = cartService.addToCart(CUSTOMER_ID, request);
        //THEN

        int resultQuantity = result.getSessionLineItems().get(0).getQuantity();
        double totalProductsPrice = result.getSessionLineItems().get(0).getProduct().getPrice();

        assertAll(
                () -> assertEquals(result.getUniqueId(), UNIQUE_CART_ID),
                () -> assertEquals(result.getSessionLineItems().size(), 1),
                () -> assertNotNull(result.getCustomer()),
                () -> assertEquals(resultQuantity, 5),
                () -> assertEquals(totalProductsPrice * resultQuantity, 10.00)
        );
    }

    @Test
    public void shouldUpdateProductQuantityInSessionCart() {
        //GIVEN
        UpdateCartRequest request = new UpdateCartRequest(
                PRODUCT_NAME,
                5);

        Product product = new Product();
        product.setName(request.getProductName());
        product.setCategory(PRODUCT_CATEGORY);
        product.setPrice(2.0);

        Optional<Customer> customer = Optional.of(new Customer(
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                CUSTOMER_EMAIL,
                PHONE_NUMBER));

        SessionCart sessionCart = new SessionCart();

        sessionCart.setUniqueId(UNIQUE_CART_ID);
        sessionCart.setOpen(Boolean.TRUE);
        sessionCart.setCustomer(customer.get());

        SessionLineItem lineItem = new SessionLineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(3);

        sessionCart.getSessionLineItems().add(lineItem);

        when(customerRepository.existsById(CUSTOMER_ID)).thenReturn(Boolean.TRUE);
        when(sessionCartRepository.findSessionCartByCustomerIdAndIsOpenTrue(CUSTOMER_ID)).thenReturn(Optional.of(sessionCart));
        //WHEN
        SessionCart result = cartService.updateProductQuantity(CUSTOMER_ID, request);
        //THEN
        int resultQuantity = result.getSessionLineItems().get(0).getQuantity();
        long resultPhoneNumber = result.getCustomer().getPhoneNumber();
        assertAll(
                () -> assertEquals(result.getUniqueId(), UNIQUE_CART_ID),
                () -> assertEquals(resultPhoneNumber, PHONE_NUMBER),
                () -> assertEquals(resultQuantity, request.getQuantity().intValue())
        );
    }

    @Test
    public void shouldRemoveProductFromSessionCart() {
        //GIVEN
        RemoveProductRequest request = new RemoveProductRequest(
                PRODUCT_NAME
        );

        Product product = new Product();
        product.setName(request.getProductName());
        product.setCategory(PRODUCT_CATEGORY);
        product.setPrice(2.0);

        Optional<Customer> customer = Optional.of(new Customer(
                CUSTOMER_ID,
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                CUSTOMER_EMAIL,
                PHONE_NUMBER));

        SessionCart sessionCart = new SessionCart();

        sessionCart.setUniqueId(UNIQUE_CART_ID);
        sessionCart.setOpen(Boolean.TRUE);
        sessionCart.setCustomer(customer.get());

        SessionLineItem lineItem = new SessionLineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(3);
        lineItem.setPrice(product.getPrice() * 3);

        sessionCart.getSessionLineItems().add(lineItem);

        when(customerRepository.existsById(CUSTOMER_ID)).thenReturn(Boolean.TRUE);
        when(sessionCartRepository.findSessionCartByCustomerIdAndIsOpenTrue(CUSTOMER_ID)).thenReturn(Optional.of(sessionCart));
        when(sessionLineItemRepository.findById(anyLong())).thenReturn(Optional.of(lineItem));
        //WHEN
        SessionCart result = cartService.removeProductFromCart(CUSTOMER_ID, request);
        //THEN

        int lineItemsSize = result.getSessionLineItems().size();

        assertAll(
                () -> assertEquals(result.getUniqueId(), UNIQUE_CART_ID),
                () -> assertEquals(lineItemsSize, 0),
                () -> assertNotNull(result.getCustomer())
        );
    }

    @Test
    public void shouldGetAllCarts() {
        //GIVEN
        CustomerArchive customerArchive = new CustomerArchive(
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                CUSTOMER_EMAIL,
                PHONE_NUMBER);

        LineItemArchive lineItemArchive = new LineItemArchive();

        CustomerDto customerDto = new CustomerDto(
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                PHONE_NUMBER,
                CUSTOMER_EMAIL
           );

        Cart cart1 = new Cart(
                UNIQUE_CART_ID + 1,
                false,
                customerArchive,
                Collections.singletonList(lineItemArchive)
        );

        Cart cart2 = new Cart(
                UNIQUE_CART_ID + 2,
                false,
                customerArchive,
                Collections.singletonList(lineItemArchive)
        );


        List<Cart> carts = new ArrayList<>();
        carts.add(cart1);
        carts.add(cart2);

        Pageable pageable = PageRequest.of(0, 1);

        when(cartRepository.findAll(pageable)).thenReturn(new PageImpl<>(carts));
        //WHEN
        Page<Cart> cartsPage = cartService.getAllCarts(pageable);
        List<Cart> result = cartsPage.get().collect(Collectors.toList());
        //THEN
        assertAll(
                () -> assertEquals(result.size(), 2)
        );
    }

    @Test
    public void shouldGetAllSessionCarts() {
        //GIVEN
        Customer customerArchive = new Customer(
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                CUSTOMER_EMAIL,
                PHONE_NUMBER);

        SessionLineItem sessionLineItem = new SessionLineItem();

        SessionCart cart1 = new SessionCart(
                UNIQUE_CART_ID + 1,
                false,
                customerArchive,
                Collections.singletonList(sessionLineItem)
        );

        SessionCart cart2 = new SessionCart(
                UNIQUE_CART_ID + 2,
                false,
                customerArchive,
                Collections.singletonList(sessionLineItem)
        );

        List<SessionCart> sessionCarts = new ArrayList<>();
        sessionCarts.add(cart1);
        sessionCarts.add(cart2);

        Pageable pageable = PageRequest.of(0, 1);

        when(sessionCartRepository.findAll(pageable)).thenReturn(new PageImpl<>(sessionCarts));
        //WHEN
        Page<SessionCart> sessionCartsPage = cartService.getSessionCarts(pageable);
        List<SessionCart> result = sessionCartsPage.get().collect(Collectors.toList());
        //THEN
        assertAll(
                () -> assertEquals(result.size(), 2)
        );
    }

    @Test
    public void shouldGetCartByUniqueId() {
        //GIVEN
        Cart cart = new Cart(
                UNIQUE_CART_ID,
                false,
                new CustomerArchive(),
                Collections.singletonList(new LineItemArchive())
        );

        when(cartRepository.findByUniqueId(UNIQUE_CART_ID)).thenReturn(Optional.of(cart));
        //WHEN
        Cart result = cartService.getCartById(UNIQUE_CART_ID);
        //THEN
        assertAll(
                () -> assertEquals(result.getUniqueId(), UNIQUE_CART_ID)
        );
    }

    @Test
    public void shouldGetSessionCartByUniqueId() {
        //GIVEN
        SessionCart sessionCart = new SessionCart(
                UNIQUE_CART_ID,
                false,
                new Customer(),
                Collections.singletonList(new SessionLineItem())
        );

        when(sessionCartRepository.findByUniqueId(UNIQUE_CART_ID)).thenReturn(Optional.of(sessionCart));
        //WHEN
        SessionCart result = cartService.getSessionCartById(UNIQUE_CART_ID);
        //THEN
        assertAll(
                () -> assertEquals(result.getUniqueId(), UNIQUE_CART_ID)
        );
    }

}