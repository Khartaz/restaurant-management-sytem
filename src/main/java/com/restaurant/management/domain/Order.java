package com.restaurant.management.domain;


import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "orders")
public class Order  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "ordered")
    private Instant ordered;

    @Column(name = "status")
    private String status;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cart cart;

    public Order() {
    }

    public Order(String orderNumber,
                 Instant ordered, String status,
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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public static class OrderBuilder {
        private String orderNumber;
        private Instant ordered;
        private String status;
        private Double totalPrice;
        private Cart cart;

        public OrderBuilder setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public OrderBuilder setOrdered(Instant ordered) {
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
