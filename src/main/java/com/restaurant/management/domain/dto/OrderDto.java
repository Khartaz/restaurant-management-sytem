package com.restaurant.management.domain.dto;

import com.restaurant.management.domain.OrderStatus;
import com.restaurant.management.domain.OrderType;

public final class OrderDto {
    private Long id;
    private String orderNumber;
    private OrderStatus orderStatus;
    private Long assignedToUserId;
    private OrderType orderType;
    private CartDto cart;

    public OrderDto() {
    }

    public OrderDto(Long id, String orderNumber, OrderStatus orderStatus,
                    Long assignedToUserId, OrderType orderType, CartDto cart) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.assignedToUserId = assignedToUserId;
        this.orderType = orderType;
        this.cart = cart;
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

    public CartDto getCart() {
        return cart;
    }
}
