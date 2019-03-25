package com.restaurant.management.service.facade;

import com.restaurant.management.domain.Order;
import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.service.OrderService;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.SendOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderFacade {

    private OrderService orderService;
    private OrderMapper orderMapper;

    @Autowired
    public OrderFacade(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        return orderMapper.mapToOrderDtoList(orders);
    }

    public OrderDto getByOrderNumber(String orderNumber) {
        Order order = orderService.getByOrderNumber(orderNumber);

        return orderMapper.mapToOrderDto(order);
    }

    public ApiResponse deleteOrder(String orderNumber) {
        return orderService.deleteOrder(orderNumber);
    }

    public OrderDto processOrder(SendOrder process) {
        Order order = orderService.processOrder(process.getPhoneNumber());

        return orderMapper.mapToOrderDto(order);
    }
 }
