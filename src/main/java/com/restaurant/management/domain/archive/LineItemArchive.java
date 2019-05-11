package com.restaurant.management.domain.archive;

import com.restaurant.management.domain.AbstractLineItem;
import com.restaurant.management.domain.RestaurantInfo;

import javax.persistence.*;

@Entity
@Table(name = "line_items_archive")
public class LineItemArchive extends AbstractLineItem {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProductArchive product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    public LineItemArchive() {
    }

    public LineItemArchive(Integer quantity, Double price, ProductArchive product) {
        super(quantity, price);
        this.product = product;
    }

    public ProductArchive getProduct() {
        return product;
    }

    public void setProduct(ProductArchive product) {
        this.product = product;
    }

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }
}
