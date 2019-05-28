package com.restaurant.management.domain;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number")
    private Long orderNumber;

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

    public Order(
                 Long id, Long orderNumber,
                 OrderStatus orderStatus,
                 Double totalPrice, Cart cart) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.cart = cart;
    }

    public Order(Long orderNumber,
                 OrderStatus orderStatus,
                 Double totalPrice, Cart cart,
                 RestaurantInfo restaurantInfo) {
        this.orderNumber = orderNumber;
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
        private OrderStatus orderStatus;
        private Double totalPrice;
        private Cart cart;
        private RestaurantInfo restaurantInfo;

        public OrderBuilder setOrderNumber(Long orderNumber) {
            this.orderNumber = orderNumber;
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
            return new Order(this.orderNumber, this.orderStatus, this.totalPrice, this.cart, this.restaurantInfo);
        }
    }

}
