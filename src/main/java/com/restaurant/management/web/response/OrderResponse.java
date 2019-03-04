package com.restaurant.management.web.response;

import java.time.Instant;

public class OrderResponse {
    private Long id;
    private String orderNumber;
    private Instant ordered;
    private String status;
    private Double totalPrice;
    private CartResponse cartResponse;


    public OrderResponse(Long id, String orderNumber, Instant ordered,
                         String status, Double totalPrice, CartResponse cartResponse) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.ordered = ordered;
        this.status = status;
        this.totalPrice = totalPrice;
        this.cartResponse = cartResponse;
    }

    public Long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Instant getOrdered() {
        return ordered;
    }

    public String getStatus() {
        return status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public CartResponse getCartResponse() {
        return cartResponse;
    }
}
