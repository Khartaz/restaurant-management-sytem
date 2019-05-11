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
    private Long orderNumber;

    @Column(name = "is_ordered")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar ordered;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    public Order() {
    }

    public Order(Long id, Long orderNumber,
                 Calendar ordered, OrderStatus orderStatus,
                 Double totalPrice, Cart cart) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.ordered = ordered;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.cart = cart;
    }

    public Order(Long orderNumber,
                 Calendar ordered, OrderStatus orderStatus,
                 Double totalPrice, Cart cart,
                 RestaurantInfo restaurantInfo) {
        this.orderNumber = orderNumber;
        this.ordered = ordered;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.cart = cart;
        this.restaurantInfo = restaurantInfo;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Calendar getOrdered() {
        return ordered;
    }

    public void setOrdered(Calendar ordered) {
        this.ordered = ordered;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
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
        private Long orderNumber;
        private Calendar ordered;
        private OrderStatus orderStatus;
        private Double totalPrice;
        private Cart cart;
        private RestaurantInfo restaurantInfo;

        public OrderBuilder setOrderNumber(Long orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public OrderBuilder setOrdered(Calendar ordered) {
            this.ordered = ordered;
            return this;
        }

        public OrderBuilder setStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
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

        public OrderBuilder setRestaurantInfo(RestaurantInfo restaurantInfo) {
            this.restaurantInfo = restaurantInfo;
            return this;
        }

        public Order build() {
            return new Order(this.orderNumber, this.ordered, this.orderStatus, this.totalPrice, this.cart, this.restaurantInfo);
        }
    }

}
