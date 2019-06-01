package com.restaurant.management.domain.archive;

import com.restaurant.management.domain.AbstractProduct;
import com.restaurant.management.domain.RestaurantInfo;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products_archive")
@Audited
public class ProductArchive extends AbstractProduct {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IngredientArchive> ingredients = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private RestaurantInfo restaurantInfo;

    public ProductArchive() {
    }

    public ProductArchive(Long createdAt, Long updatedAt,
                          String createdByUserId, String updatedByUserId,
                          Long id, String name, String category,
                          Double price, List<IngredientArchive> ingredients) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, category, price);
        this.ingredients = ingredients;
    }

    public ProductArchive(String name, String category, Double price,
                          List<IngredientArchive> ingredients, RestaurantInfo restaurantInfo) {
        super(name, category, price);
        this.ingredients = ingredients;
        this.restaurantInfo = restaurantInfo;
    }

    public ProductArchive(String name, String category, Double price,
                          List<IngredientArchive> ingredients) {
        super(name, category, price);
        this.ingredients = ingredients;
    }

    public List<IngredientArchive> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientArchive> ingredients) {
        this.ingredients = ingredients;
    }

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }

    public static class ProductArchiveBuilder {
        private String name;
        private String category;
        private Double price;
        private List<IngredientArchive> ingredients = new ArrayList<>();
        private RestaurantInfo restaurantInfo;

        public ProductArchiveBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductArchiveBuilder setIngredientsList(List<IngredientArchive> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public ProductArchiveBuilder setCategory(String category) {
            this.category = category;
            return this;
        }

        public ProductArchiveBuilder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public ProductArchiveBuilder setRestaurantInfo(RestaurantInfo restaurantInfo) {
            this.restaurantInfo = restaurantInfo;
            return this;
        }

        public ProductArchive build() {
            return new ProductArchive(this.name, this.category, this.price, this.ingredients, this.restaurantInfo);
        }
    }
}
