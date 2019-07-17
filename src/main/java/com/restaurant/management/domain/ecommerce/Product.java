package com.restaurant.management.domain.ecommerce;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Audited
public class Product extends AbstractProduct {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    public Product() {
    }

    public Product(Long createdAt, Long updatedAt,
                   String createdByUserId, String updatedByUserId,
                   Long id, String name, String category,
                   Double price, List<Ingredient> ingredients) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, category, price);
        this.ingredients = ingredients;
    }

    public Product(Long id, String name, String category,
                   Double price, List<Ingredient> ingredients) {
        super(id, name, category, price);
        this.ingredients = ingredients;
    }

    public Product(String name, String category,
                   Double price, List<Ingredient> ingredients, RestaurantInfo restaurantInfo) {
        super(name, category, price);
        this.ingredients = ingredients;
        this.restaurantInfo = restaurantInfo;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public static class ProductBuilder {
        private String name;
        private String category;
        private Double price;
        private List<Ingredient> ingredients;
        private RestaurantInfo restaurantInfo;

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setCategory(String category) {
            this.category = category;
            return this;
        }

        public ProductBuilder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public ProductBuilder setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public ProductBuilder setRestaurantInfo(RestaurantInfo restaurantInfo) {
            this.restaurantInfo = restaurantInfo;
            return this;
        }

        public Product build() {
            return new Product(this.name, this.category,
                    this.price, this.ingredients, this.restaurantInfo);
        }
    }

}
