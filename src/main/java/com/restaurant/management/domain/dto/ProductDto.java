package com.restaurant.management.domain.dto;

import com.restaurant.management.domain.Ingredient;

import java.time.Instant;
import java.util.List;

public class ProductDto {

    private Long id;
    private String name;
    private String category;
    private Double price;
    private List<Ingredient> ingredients;
    private Instant createdAt;

    public ProductDto(Long id, String name, String category, Double price,
                      List<Ingredient> ingredients, Instant createdAt) {
        this.id = id;
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
}
