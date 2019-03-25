package com.restaurant.management.service;

import com.restaurant.management.domain.*;
import com.restaurant.management.domain.archive.CustomerArchive;
import com.restaurant.management.domain.archive.LineItemArchive;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.repository.*;
import com.restaurant.management.web.request.cart.RegisterCartRequest;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTestSuite {
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

    private static final long PHONE_NUMBER = 684293190L;
    private static final String CUSTOMER_NAME = "Customer name";
    private static final String CUSTOMER_LASTNAME = "Customer lastname";
    private static final String CUSTOMER_EMAIL = "customer@email.com";

    private static final String PRODUCT_NAME = "Product name";
    private static final String PRODUCT_CATEGORY = "Product category";

    @Test
    public void shouldOpenSessionCart() {
        //GIVEN
        RegisterCartRequest request = new RegisterCartRequest(PHONE_NUMBER);

        Optional<Customer> customer = Optional.of(new Customer(
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                CUSTOMER_EMAIL,
                PHONE_NUMBER));

        when(customerRepository.findByPhoneNumber(PHONE_NUMBER)).thenReturn(customer);
        when(sessionCartRepository.existsByCustomerAndIsOpenTrue(customer.get())).thenReturn(Boolean.FALSE);
        //WHEN
        SessionCart result = cartService.openSessionCart(request);
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
                PHONE_NUMBER,
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
                request.getPhoneNumber()));

        SessionCart sessionCart = new SessionCart();

        sessionCart.setUniqueId(UNIQUE_CART_ID);
        sessionCart.setOpen(Boolean.TRUE);
        sessionCart.setCustomer(customer.get());

        when(customerRepository.existsByPhoneNumber(PHONE_NUMBER)).thenReturn(Boolean.TRUE);
        when(productRepository.findProductByName(anyString())).thenReturn(Optional.of(product));
        when(sessionCartRepository.findSessionCartByCustomerPhoneNumberAndIsOpenTrue(PHONE_NUMBER)).thenReturn(Optional.of(sessionCart));
        //WHEN
        SessionCart result = cartService.addToCart(request);
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
                PHONE_NUMBER,
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
                request.getPhoneNumber()));

        SessionCart sessionCart = new SessionCart();

        sessionCart.setUniqueId(UNIQUE_CART_ID);
        sessionCart.setOpen(Boolean.TRUE);
        sessionCart.setCustomer(customer.get());

        SessionLineItem lineItem = new SessionLineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(3);

        sessionCart.getSessionLineItems().add(lineItem);

        when(customerRepository.existsByPhoneNumber(PHONE_NUMBER)).thenReturn(Boolean.TRUE);
        when(sessionCartRepository.findSessionCartByCustomerPhoneNumberAndIsOpenTrue(PHONE_NUMBER)).thenReturn(Optional.of(sessionCart));
        //WHEN
        SessionCart result = cartService.updateProductQuantity(request);
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
                PHONE_NUMBER,
                PRODUCT_NAME
        );

        Product product = new Product();
        product.setName(request.getProductName());
        product.setCategory(PRODUCT_CATEGORY);
        product.setPrice(2.0);

        Optional<Customer> customer = Optional.of(new Customer(
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                CUSTOMER_EMAIL,
                request.getPhoneNumber()));

        SessionCart sessionCart = new SessionCart();

        sessionCart.setUniqueId(UNIQUE_CART_ID);
        sessionCart.setOpen(Boolean.TRUE);
        sessionCart.setCustomer(customer.get());

        SessionLineItem lineItem = new SessionLineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(3);
        lineItem.setId(1L);
        lineItem.setPrice(product.getPrice() * 3);

        sessionCart.getSessionLineItems().add(lineItem);

        when(customerRepository.existsByPhoneNumber(PHONE_NUMBER)).thenReturn(Boolean.TRUE);
        when(sessionCartRepository.findSessionCartByCustomerPhoneNumberAndIsOpenTrue(PHONE_NUMBER)).thenReturn(Optional.of(sessionCart));
        when(sessionLineItemRepository.findById(anyLong())).thenReturn(Optional.of(lineItem));
        //WHEN
        SessionCart result = cartService.removeProductFromCart(request);
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

        when(cartRepository.findAll()).thenReturn(carts);
        //WHEN
        List<Cart> result = cartService.getAllCarts();
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


        when(sessionCartRepository.findAll()).thenReturn(sessionCarts);
        //WHEN
        List<SessionCart> result = cartService.getSessionCarts();
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
        Cart result = cartService.getCartByUniqueId(UNIQUE_CART_ID);
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
        SessionCart result = cartService.getSessionCartByUniqueId(UNIQUE_CART_ID);
        //THEN
        assertAll(
                () -> assertEquals(result.getUniqueId(), UNIQUE_CART_ID)
        );
    }

}