package com.restaurant.management.domain;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private Double price;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    @Column(name = "createdAt")
    private Instant createdAt;

    public Product() {
    }

    public Product(Long id, String name, String category, Double price,
                   List<Ingredient> ingredients, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.ingredients = ingredients;
        this.createdAt = createdAt;
    }

    public Product(String name, String category, double price,
                   List<Ingredient> ingredients, Instant createdAt) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.ingredients = ingredients;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public static class ProductBuilder {
        private String name;
        private String category;
        private Double price;
        private List<Ingredient> ingredients;
        private Instant createdAt;

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

        public ProductBuilder setCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Product build() {
            return new Product(this.name, this.category, this.price, this.ingredients, this.createdAt);
        }
    }

}
