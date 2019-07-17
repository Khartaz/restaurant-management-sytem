package com.restaurant.management.web.response;

import com.restaurant.management.domain.ecommerce.OrderStatus;
import com.restaurant.management.domain.ecommerce.OrderType;

public final class OrderResponse {
    private Long id;
    private String orderNumber;
    private OrderStatus orderStatus;
    private Long assignedToUserId;
    private OrderType orderType;
    private CartResponse cartResponse;

    public OrderResponse() {
    }

    public OrderResponse(Long id, String orderNumber, OrderStatus orderStatus,
                         Long assignedToUserId, OrderType orderType, CartResponse cartResponse) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.assignedToUserId = assignedToUserId;
        this.orderType = orderType;
        this.cartResponse = cartResponse;
    }

    public Long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Long getAssignedToUserId() {
        return assignedToUserId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public CartResponse getCartResponse() {
        return cartResponse;
    }
}
