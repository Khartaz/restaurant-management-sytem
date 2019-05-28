package com.restaurant.management.web.response;

import com.restaurant.management.domain.OrderStatus;

import java.util.Calendar;

public final class OrderResponse {
    private Long id;
    private Long orderNumber;
    private OrderStatus orderStatus;
    private Double totalPrice;
    private CartResponse cartResponse;

    public OrderResponse() {
    }

    public OrderResponse(Long id, Long orderNumber, OrderStatus orderStatus, Double totalPrice, CartResponse cartResponse) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.cartResponse = cartResponse;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public CartResponse getCartResponse() {
        return cartResponse;
    }
}
