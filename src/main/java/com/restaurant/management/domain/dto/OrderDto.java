package com.restaurant.management.domain.dto;

import java.time.Instant;

public class OrderDto {

    private Long id;
    private String orderNumber;
    private Instant ordered;
    private String status;
    private Double totalPrice;
    private CartDto cart;

    public OrderDto(Long id, String orderNumber, Instant ordered, String status, Double totalPrice, CartDto cart) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.ordered = ordered;
        this.status = status;
        this.totalPrice = totalPrice;
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CartDto getCart() {
        return cart;
    }

    public void setCart(CartDto cart) {
        this.cart = cart;
    }
}
