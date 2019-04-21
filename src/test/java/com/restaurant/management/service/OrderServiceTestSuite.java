package com.restaurant.management.service;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.Order;
import com.restaurant.management.domain.archive.CustomerArchive;
import com.restaurant.management.domain.archive.LineItemArchive;
import com.restaurant.management.domain.archive.ProductArchive;
import com.restaurant.management.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTestSuite {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CartService cartService;

    private static final String CART_UNIQUE_ID = "DK3S4D";

    private static final String CUSTOMER_NAME = "Customer name";
    private static final String CUSTOMER_LASTNAME = "Customer lastname";
    private static final String CUSTOMER_EMAIL = "Customer email";
    private static final long PHONE_NUMBER = 9289310L;

    private static final String ORDER_NUMBER = "MDA2KEOA";

    @Test
    public void shouldFetchCreateNewOrderWithCart() {
        //GIVEN
        CustomerArchive customerArchive = new CustomerArchive(
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                CUSTOMER_EMAIL,
                PHONE_NUMBER
        );

        List<LineItemArchive> lineItemArchives = new ArrayList<>();
        lineItemArchives.add(new LineItemArchive(5, 2.20, new ProductArchive()));
        lineItemArchives.add(new LineItemArchive(3, 3.50, new ProductArchive()));
        lineItemArchives.add(new LineItemArchive(4, 4.00, new ProductArchive()));

        Cart cart = new Cart(
                CART_UNIQUE_ID,
                Boolean.FALSE,
                customerArchive,
                lineItemArchives
        );

        when(cartService.confirmCart(PHONE_NUMBER)).thenReturn(cart);
        //WHEN
        Order result = orderService.processOrder(PHONE_NUMBER);
        //THEN
        assertAll(
                () -> assertEquals(result.getCart(), cart),
                () -> assertEquals(result.getOrderNumber().length(), 5),
                () -> assertEquals(result.getStatus(), "ORDERED")
        );
    }

    @Test
    public void shouldFetchGetOrderByOrderNumber() {
        //GIVEN
        CustomerArchive customerArchive = new CustomerArchive(
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                CUSTOMER_EMAIL,
                PHONE_NUMBER
        );

        List<LineItemArchive> lineItemArchives = new ArrayList<>();
        lineItemArchives.add(new LineItemArchive(5, 2.20, new ProductArchive()));
        lineItemArchives.add(new LineItemArchive(3, 3.50, new ProductArchive()));
        lineItemArchives.add(new LineItemArchive(4, 4.00, new ProductArchive()));

        Cart cart = new Cart(
                CART_UNIQUE_ID,
                Boolean.FALSE,
                customerArchive,
                lineItemArchives
        );

        Order order = new Order();
        order.setOrderNumber(ORDER_NUMBER);
        order.setTotalPrice(200.00);
        order.setCart(cart);

        when(orderRepository.findByOrderNumber(ORDER_NUMBER)).thenReturn(java.util.Optional.of(order));
        //WHEN
        Order result = orderService.getByOrderNumber(ORDER_NUMBER);
        //THEN
        assertAll(
                () -> assertEquals(result.getOrderNumber(), ORDER_NUMBER),
                () -> assertEquals(result.getCart().getUniqueId(), cart.getUniqueId()),
                () -> assertEquals(result.getCart().getCustomer().getPhoneNumber(), customerArchive.getPhoneNumber()),
                () -> assertEquals(result.getTotalPrice().doubleValue(), 200.00)
        );
    }

    @Test
    public void shouldFetchGetAllOrders() {
        //GIVEN
        CustomerArchive customerArchive = new CustomerArchive(
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                CUSTOMER_EMAIL,
                PHONE_NUMBER
        );

        List<LineItemArchive> lineItemArchives = new ArrayList<>();
        lineItemArchives.add(new LineItemArchive(5, 2.20, new ProductArchive()));
        lineItemArchives.add(new LineItemArchive(3, 3.50, new ProductArchive()));
        lineItemArchives.add(new LineItemArchive(4, 4.00, new ProductArchive()));

        Cart cart1 = new Cart(
                CART_UNIQUE_ID,
                Boolean.FALSE,
                customerArchive,
                lineItemArchives
        );

        Cart cart2 = new Cart(
                CART_UNIQUE_ID,
                Boolean.FALSE,
                customerArchive,
                lineItemArchives
        );

        Order order1 = new Order.OrderBuilder()
                .setOrdered(Calendar.getInstance())
                .setStatus("ORDERED")
                .setOrderNumber(ORDER_NUMBER)
                .setTotalPrice(new Order().calculateTotalPrice(cart1))
                .setCart(cart1)
                .build();

        Order order2 = new Order.OrderBuilder()
                .setOrdered(Calendar.getInstance())
                .setStatus("ORDERED")
                .setOrderNumber(ORDER_NUMBER + 1)
                .setTotalPrice(new Order().calculateTotalPrice(cart2))
                .setCart(cart2)
                .build();

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        Pageable pageable = PageRequest.of(0,1);

        when(orderRepository.findAll(pageable)).thenReturn(new PageImpl<>(orders));
        //WHEN
        Page<Order> ordersPage = orderService.getAllOrders(pageable);
        List<Order> result = ordersPage.get().collect(Collectors.toList());
        //THEN
        assertAll(
                () -> assertEquals(result.size(), orders.size()),
                () -> assertEquals(result.get(0).getCart().getUniqueId(), cart1.getUniqueId()),
                () -> assertEquals(result.get(1).getCart().getCustomer().getPhoneNumber(), customerArchive.getPhoneNumber())
        );
    }
}