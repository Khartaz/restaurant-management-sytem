package com.restaurant.management.domain;

import com.restaurant.management.domain.archive.CustomerArchive;
import com.restaurant.management.domain.archive.LineItemArchive;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart extends AbstractCart {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CustomerArchive customerArchive;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LineItemArchive> lineItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private RestaurantInfo restaurantInfo;

    public Cart() {
    }

    public Cart(Boolean isOpen, Double totalPrice,
                CustomerArchive customerArchive, List<LineItemArchive> lineItems) {
        super(isOpen, totalPrice);
        this.customerArchive = customerArchive;
        this.lineItems = lineItems;
    }

    public Cart(Boolean isOpen, Double totalPrice,
                CustomerArchive customerArchive,
                List<LineItemArchive> lineItems, RestaurantInfo restaurantInfo) {
        super(isOpen, totalPrice);
        this.customerArchive = customerArchive;
        this.lineItems = lineItems;
        this.restaurantInfo = restaurantInfo;
    }

    public CustomerArchive getCustomerArchive() {
        return customerArchive;
    }

    public void setCustomerArchive(CustomerArchive customerArchive) {
        this.customerArchive = customerArchive;
    }

    public List<LineItemArchive> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItemArchive> lineItems) {
        this.lineItems = lineItems;
    }

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }

    public static class CartBuilder {
        private Boolean isOpen;
        private Double totalPrice;
        private CustomerArchive customerArchive;
        private List<LineItemArchive> lineItems;
        private RestaurantInfo restaurantInfo;

        public CartBuilder setIsOpen(Boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }

        public CartBuilder setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public CartBuilder setCustomerArchive(CustomerArchive customerArchive) {
            this.customerArchive = customerArchive;
            return this;
        }

        public CartBuilder setLineItems(List<LineItemArchive> lineItems) {
            this.lineItems = lineItems;
            return this;
        }

        public CartBuilder setRestaurantInfo(RestaurantInfo restaurantInfo) {
            this.restaurantInfo = restaurantInfo;
            return this;
        }

        public Cart build() {
            return new Cart(this.isOpen, this.totalPrice, this.customerArchive, this.lineItems, this.restaurantInfo);
        }
    }
}
