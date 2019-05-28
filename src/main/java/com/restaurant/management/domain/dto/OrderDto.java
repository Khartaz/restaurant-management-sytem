package com.restaurant.management.domain.dto;

import com.restaurant.management.domain.OrderStatus;

import java.util.Calendar;

public final class OrderDto {
    private Long id;
    private Long orderNumber;
    private OrderStatus orderStatus;
    private Double totalPrice;
    private CartDto cart;

    public OrderDto() {
    }

    public OrderDto(Long id, Long orderNumber, OrderStatus orderStatus,
                    Double totalPrice, CartDto cart) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.cart = cart;
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

    public CartDto getCart() {
        return cart;
    }
}
