package com.restaurant.management.domain.archive;

import com.restaurant.management.domain.ecommerce.AbstractLineItem;
import com.restaurant.management.domain.ecommerce.RestaurantInfo;

import javax.persistence.*;

@Entity
@Table(name = "line_items_archive")
public class LineItemArchive extends AbstractLineItem {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProductArchive product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private RestaurantInfo restaurantInfo;

    public LineItemArchive() {
    }

    public LineItemArchive(Integer quantity, Double price, ProductArchive product) {
        super(quantity, price);
        this.product = product;
    }

    public LineItemArchive(Integer quantity, Double price,
                           ProductArchive product, RestaurantInfo restaurantInfo) {
        super(quantity, price);
        this.product = product;
        this.restaurantInfo = restaurantInfo;
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


    public static class LineItemArchiveBuilder {
        private Integer quantity;
        private Double price;
        private ProductArchive productArchive;
        private RestaurantInfo restaurantInfo;

        public LineItemArchiveBuilder setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public LineItemArchiveBuilder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public LineItemArchiveBuilder setProductArchive(ProductArchive productArchive) {
            this.productArchive = productArchive;
            return this;
        }

        public LineItemArchiveBuilder setRestaurantInfo(RestaurantInfo restaurantInfo) {
            this.restaurantInfo = restaurantInfo;
            return this;
        }

        public LineItemArchive build() {
            return new LineItemArchive(this.quantity, this.price, this.productArchive, this.restaurantInfo);
        }
    }

}
