package com.restaurant.management.service;

import com.restaurant.management.domain.Order;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Long countRestaurantOrders(@CurrentUser UserPrincipal currentUser);

    Order processOrder(@CurrentUser UserPrincipal currentUser, Long customerId);

    Page<Order> getAllOrders(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    Order getByOrderId(@CurrentUser UserPrincipal currentUser, Long orderId);

    ApiResponse deleteOrder(@CurrentUser UserPrincipal currentUser, Long orderId);

    Page<Order> getCustomerOrdersById(@CurrentUser UserPrincipal currentUser, Long customerId, Pageable pageable);

    Order getOrderByCustomerIdAndOrderId(@CurrentUser UserPrincipal currentUser, Long customerId, Long orderId);
}
