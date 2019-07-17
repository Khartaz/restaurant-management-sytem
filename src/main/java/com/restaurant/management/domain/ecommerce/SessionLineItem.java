package com.restaurant.management.domain.ecommerce;

import javax.persistence.*;

@Entity
@Table(name = "session_line_items")
public class SessionLineItem extends AbstractLineItem {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    public SessionLineItem() {
    }

    public SessionLineItem(Long id, Integer quantity, Double price, Product product) {
        super(id, quantity, price);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }
}
