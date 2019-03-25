package com.restaurant.management.service;

import com.restaurant.management.domain.*;
import com.restaurant.management.exception.order.OrderMessages;
import com.restaurant.management.exception.order.OrderNotFoundException;
import com.restaurant.management.repository.*;
import com.restaurant.management.utils.Utils;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private OrderRepository orderRepository;
    private CartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    public Order processOrder(Long phoneNumber) {
        Cart cart = cartService.confirmCart(phoneNumber);

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

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
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
}
