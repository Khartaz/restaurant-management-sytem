package com.restaurant.management.service.ecommerce.facade;

import com.restaurant.management.domain.ecommerce.Order;
import com.restaurant.management.domain.ecommerce.dto.OrderDto;
import com.restaurant.management.mapper.ecommerce.OrderMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.OrderService;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public final class OrderFacade {

    private OrderService orderService;
    private OrderMapper orderMapper;

    @Autowired
    public OrderFacade(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    public Page<OrderDto> getAllOrders(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<Order> orders = orderService.getAllOrders(currentUser, pageable);

        return orderMapper.mapToOrderDtoPage(orders);
    }

    public OrderDto getByOrderNumber(@CurrentUser UserPrincipal currentUser, Long orderId) {
        Order order = orderService.getByOrderId(currentUser, orderId);

        return orderMapper.mapToOrderDto(order);
    }

    public ApiResponse deleteOrder(@CurrentUser UserPrincipal currentUser, Long orderId) {
        return orderService.deleteOrder(currentUser, orderId);
    }

    public Page<OrderDto> getCustomerOrdersById(@CurrentUser UserPrincipal currentUser, Long customerId, Pageable pageable) {
        Page<Order> orders = orderService.getCustomerOrdersById(currentUser, customerId, pageable);

        return orderMapper.mapToOrderDtoPage(orders);
    }

    public OrderDto getOrderByCustomerIdAndOrderId(@CurrentUser UserPrincipal currentUser, Long customerId, Long orderId) {
        Order order = orderService.getOrderByCustomerIdAndOrderId(currentUser, customerId, orderId);

        return orderMapper.mapToOrderDto(order);
    }

    public Long countRestaurantOrders(@CurrentUser UserPrincipal currentUser) {
        return orderService.countCompanyOrders(currentUser);
    }

    public Page<OrderDto> getAllOfCurrentYear(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<Order> orders = orderService.getAllOfCurrentYear(currentUser, pageable);

        return orderMapper.mapToOrderDtoPage(orders);
    }
 }
