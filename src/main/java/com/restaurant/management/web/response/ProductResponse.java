package com.restaurant.management.web.response;

import java.util.List;

public final class ProductResponse {

    private Long createdAt;
    private Long updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long id;
    private String name;
    private String category;
    private Double price;
    private List<IngredientResponse> ingredients;

    public ProductResponse() {
    }

    public ProductResponse(Long createdAt, Long updatedAt,
                           String createdBy, String updatedBy, Long id,
                           String name, String category, Double price,
                           List<IngredientResponse> ingredients) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.ingredients = ingredients;
    }

    public ProductResponse(Long id, String name,
                           String category, Double price,
                           List<IngredientResponse> ingredients) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.ingredients = ingredients;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public List<IngredientResponse> getIngredients() {
        return ingredients;
    }
}
