package com.restaurant.management.service;

import com.restaurant.management.domain.*;
import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.exception.order.OrderMessages;
import com.restaurant.management.exception.order.OrderNotFoundException;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.repository.*;
import com.restaurant.management.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private OrderRepository orderRepository;
    private Utils utils;
    private OrderMapper orderMapper;
    private CartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        Utils utils,
                        OrderMapper orderMapper,
                        CartService cartService) {
        this.orderRepository = orderRepository;
        this.utils = utils;
        this.orderMapper = orderMapper;
        this.cartService = cartService;
    }

    public OrderDto processOrder(Long phoneNumber) {
        Cart cart = cartService.confirmCart(phoneNumber);

        Order order = new Order.OrderBuilder()
                .setOrdered(new Date().toInstant())
                .setStatus("ORDERED")
                .setOrderNumber(utils.generateOrderNumber(5))
                .setTotalPrice(new Order().calculateTotalPrice(cart))
                .setCart(cart)
                .build();

        orderRepository.save(order);

        return orderMapper.mapToOrderDto(order);
    }

    public List<OrderDto> showOrders() {
        List<Order> orders = orderRepository.findAll();

        return orderMapper.mapToOrderDtoList(orders);
    }

    public OrderDto getByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_NUMBER_NOT_FOUND.getMessage() + orderNumber));

        return orderMapper.mapToOrderDto(order);
    }

    public void deleteOrder(String orderNumber) {
        Order order = orderMapper.mapToOrder(getByOrderNumber(orderNumber));

        orderRepository.deleteById(order.getId());
    }
}
