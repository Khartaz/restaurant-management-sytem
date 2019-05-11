package com.restaurant.management.web.response;

import com.restaurant.management.domain.OrderStatus;

import java.util.Calendar;

public final class OrderResponse {
    private Long id;
    private Long orderNumber;
    private Calendar ordered;
    private OrderStatus orderStatus;
    private Double totalPrice;
    private CartResponse cartResponse;

    public OrderResponse() {
    }

    public OrderResponse(Long id, Long orderNumber, Calendar ordered,
                         OrderStatus orderStatus, Double totalPrice, CartResponse cartResponse) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.ordered = ordered;
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

    public Calendar getOrdered() {
        return ordered;
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
