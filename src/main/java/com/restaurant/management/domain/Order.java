package com.restaurant.management.domain;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "orders")
public class Order  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "is_ordered")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar ordered;

    @Column(name = "status")
    private String status;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    public Order() {
    }

    public Order(Long id, String orderNumber,
                 Calendar ordered, String status,
                 Double totalPrice, Cart cart) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.ordered = ordered;
        this.status = status;
        this.totalPrice = totalPrice;
        this.cart = cart;
    }

    public Order(String orderNumber,
                 Calendar ordered, String status,
                 Double totalPrice, Cart cart) {
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

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Calendar getOrdered() {
        return ordered;
    }

    public void setOrdered(Calendar ordered) {
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

    public Double calculateTotalPrice(Cart cart) {
         double price = Stream.of(cart.getLineItems())
                .flatMapToDouble(v -> v.stream()
                        .mapToDouble(AbstractLineItem::getPrice))
                .sum();

        return Math.floor(price * 100) / 100;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }

    public static class OrderBuilder {
        private String orderNumber;
        private Calendar ordered;
        private String status;
        private Double totalPrice;
        private Cart cart;

        public OrderBuilder setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public OrderBuilder setOrdered(Calendar ordered) {
            this.ordered = ordered;
            return this;
        }

        public OrderBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        public OrderBuilder setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public OrderBuilder setCart(Cart cart) {
            this.cart = cart;
            return this;
        }

        public Order build() {
            return new Order(this.orderNumber, this.ordered, this.status, this.totalPrice, this.cart);
        }
    }

}
