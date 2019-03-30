package com.restaurant.management.service.facade;

import com.restaurant.management.domain.Order;
import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.service.OrderService;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class OrderFacade {

    private OrderService orderService;
    private OrderMapper orderMapper;

    @Autowired
    public OrderFacade(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    public Page<OrderDto> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderService.getAllOrders(pageable);

        return orderMapper.mapToProductDtoPage(orders);
    }

    public OrderDto getByOrderNumber(String orderNumber) {
        Order order = orderService.getByOrderNumber(orderNumber);

        return orderMapper.mapToOrderDto(order);
    }

    public ApiResponse deleteOrder(String orderNumber) {
        return orderService.deleteOrder(orderNumber);
    }

    public OrderDto processOrder(Long id) {
        Order order = orderService.processOrder(id);

        return orderMapper.mapToOrderDto(order);
    }

    public Page<OrderDto> getOrdersByCustomerId(Long id, Pageable pageable) {
        Page<Order> orders = orderService.getOrdersByCustomerId(id, pageable);

        return orderMapper.mapToProductDtoPage(orders);
    }

    public OrderDto getOrderByCustomerIdAndOrderNumber(Long id, String orderNumber) {
        Order order = orderService.getOrderByCustomerIdAndOrderNumber(id, orderNumber);

        return orderMapper.mapToOrderDto(order);
    }
 }
