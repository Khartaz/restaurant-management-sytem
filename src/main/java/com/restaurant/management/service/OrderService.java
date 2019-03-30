package com.restaurant.management.service;

import com.restaurant.management.domain.*;
import com.restaurant.management.exception.customer.CustomerMessages;
import com.restaurant.management.exception.customer.CustomerNotFoundException;
import com.restaurant.management.exception.order.OrderMessages;
import com.restaurant.management.exception.order.OrderNotFoundException;
import com.restaurant.management.repository.*;
import com.restaurant.management.utils.Utils;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private OrderRepository orderRepository;
    private CartService cartService;
    private CustomerRepository customerRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CartService cartService,
                        CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.customerRepository = customerRepository;
    }

    public Order processOrder(Long id) {
        Cart cart = cartService.confirmCart(id);

        Order order = new Order.OrderBuilder()
                .setOrdered(new Date().toInstant())
                .setStatus("ORDERED")
                .setOrderNumber(Utils.generateOrderNumber(5))
                .setTotalPrice(new Order().calculateTotalPrice(cart))
                .setCart(cart)
                .build();

        orderRepository.save(order);

        return order;
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order getByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_NUMBER_NOT_FOUND.getMessage() + orderNumber));
    }

    public ApiResponse deleteOrder(String orderNumber) {
        Order order = getByOrderNumber(orderNumber);

        orderRepository.deleteById(order.getId());

        return new ApiResponse(true, OrderMessages.ORDER_DELETED.getMessage());
    }

    public Page<Order> getOrdersByCustomerId(Long id, Pageable pageable) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage()));

        Page<Order> orderList = orderRepository.findAll(pageable);

        List<Order> customerOrders = orderList.stream()
                .filter(v -> v.getCart()
                        .getCustomer()
                        .getPhoneNumber()
                        .equals(customer.getPhoneNumber()))
                .collect(Collectors.toList());

        return new PageImpl<>(customerOrders);
    }

    public Order getOrderByCustomerIdAndOrderNumber(Long id, String orderNumber) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage()));

        List<Order> orderList = orderRepository.findAll();

        return orderList.stream()
                .filter(v -> v.getCart()
                .getCustomer().getPhoneNumber()
                        .equals(customer.getPhoneNumber()))
                .collect(Collectors.toList()).stream()
                    .filter(v -> v.getOrderNumber().equals(orderNumber))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_NUMBER_NOT_FOUND.getMessage()));
    }
}
