package com.restaurant.management.web.response;

import java.time.Instant;

public class OrderResponse {

    private String orderNumber;
    private Instant ordered;
    private String status;
    private Double totalPrice;
    private CartResponse cartResponse;


    public OrderResponse(String orderNumber, Instant ordered,
                         String status, Double totalPrice, CartResponse cartResponse) {
        this.orderNumber = orderNumber;
        this.ordered = ordered;
        this.status = status;
        this.totalPrice = totalPrice;
        this.cartResponse = cartResponse;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Instant getOrdered() {
        return ordered;
    }

    public void setOrdered(Instant ordered) {
        this.ordered = ordered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CartResponse getCartResponse() {
        return cartResponse;
    }

    public void setCartResponse(CartResponse cartResponse) {
        this.cartResponse = cartResponse;
    }
}
