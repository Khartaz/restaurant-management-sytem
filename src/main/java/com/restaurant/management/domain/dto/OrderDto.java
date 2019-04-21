package com.restaurant.management.domain.dto;

import java.util.Calendar;

public final class OrderDto {

    private Long id;
    private String orderNumber;
    private Calendar ordered;
    private String status;
    private Double totalPrice;
    private CartDto cart;

    public OrderDto() {
    }

    public OrderDto(Long id, String orderNumber, Calendar ordered,
                    String status, Double totalPrice, CartDto cart) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.ordered = ordered;
        this.status = status;
        this.totalPrice = totalPrice;
        this.cart = cart;
    }

    public OrderDto(String orderNumber, Calendar ordered,
                    String status, Double totalPrice, CartDto cart) {
        this.orderNumber = orderNumber;
        this.ordered = ordered;
        this.status = status;
        this.totalPrice = totalPrice;
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Calendar getOrdered() {
        return ordered;
    }

    public String getStatus() {
        return status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public CartDto getCart() {
        return cart;
    }
}
