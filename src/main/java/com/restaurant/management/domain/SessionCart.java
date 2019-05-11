package com.restaurant.management.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "session_carts")
public class SessionCart extends AbstractCart {

    @OneToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SessionLineItem> sessionLineItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    public SessionCart() {
    }

    public SessionCart(Long id, Boolean isOpen,
                       Customer customer, List<SessionLineItem> sessionLineItems) {
        super(id, isOpen);
        this.customer = customer;
        this.sessionLineItems = sessionLineItems;
    }

    public SessionCart(Boolean isOpen,
                       Customer customer, List<SessionLineItem> sessionLineItems) {
        super(isOpen);
        this.customer = customer;
        this.sessionLineItems = sessionLineItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<SessionLineItem> getSessionLineItems() {
        return sessionLineItems;
    }

    public void setSessionLineItems(List<SessionLineItem> sessionLineItems) {
        this.sessionLineItems = sessionLineItems;
    }

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }

    public Double calculateTotal(){
        return sessionLineItems.stream()
                .mapToDouble(v ->v.getPrice() * v.getQuantity())
                .reduce(Double::sum)
                .getAsDouble();
    }
}
