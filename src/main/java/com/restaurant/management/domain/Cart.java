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
    private CustomerArchive customer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LineItemArchive> lineItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private RestaurantInfo restaurantInfo;

    public Cart() {
    }

    public Cart(Boolean isOpen, Double totalPrice,
                CustomerArchive customer, List<LineItemArchive> lineItems) {
        super(isOpen, totalPrice);
        this.customer = customer;
        this.lineItems = lineItems;
    }

    public CustomerArchive getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerArchive customer) {
        this.customer = customer;
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
}
