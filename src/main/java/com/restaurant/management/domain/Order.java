package com.restaurant.management.domain;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "orders")
public class Order extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus;

    @Column(name = "assigned_to_user_id")
    private Long assignedToUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    public Order() {
    }

    public Order(
            Long id, String orderNumber,
            OrderStatus orderStatus, Long assignedToUserId,
            OrderType orderType, Cart cart) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.assignedToUserId = assignedToUserId;
        this.orderType = orderType;
        this.cart = cart;
    }

    public Order(String orderNumber,
                 OrderStatus orderStatus, Long assignedToUserId,
                 OrderType orderType, Cart cart,
                 RestaurantInfo restaurantInfo) {
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.assignedToUserId = assignedToUserId;
        this.orderType = orderType;
        this.cart = cart;
        this.restaurantInfo = restaurantInfo;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
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

    public Long getAssignedToUserId() {
        return assignedToUserId;
    }

    public void setAssignedToUserId(Long assignedToUserId) {
        this.assignedToUserId = assignedToUserId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public static String createOrderNumber(String orderNumber) {
        int orderYear;

        Calendar calendar = Calendar.getInstance();
        orderYear = calendar.get(Calendar.YEAR);

        return orderNumber + "/" + orderYear;
    }

    public static class OrderBuilder {
        private String orderNumber;
        private OrderStatus orderStatus;
        private Long assignedTo;
        private OrderType orderType;
        private Cart cart;
        private RestaurantInfo restaurantInfo;

        public OrderBuilder setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public OrderBuilder setStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public OrderBuilder setAssignedTo(Long assignedTo) {
            this.assignedTo = assignedTo;
            return this;
        }

        public OrderBuilder setOrderType(OrderType orderType) {
            this.orderType = orderType;
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
            return new Order(this.orderNumber, this.orderStatus, this.assignedTo, this.orderType, this.cart, this.restaurantInfo);
        }
    }

}
