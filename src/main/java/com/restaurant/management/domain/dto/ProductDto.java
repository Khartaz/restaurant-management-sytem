package com.restaurant.management.domain.dto;

import java.util.List;

public final class ProductDto {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String name;
    private String category;
    private Double price;
    private List<IngredientDto> ingredients;

    public ProductDto() {
    }

    public ProductDto(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                      Long id, String name,
                      String category, Double price, List<IngredientDto> ingredients) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.ingredients = ingredients;
    }

    public ProductDto(String name, String category,
                      Double price, List<IngredientDto> ingredients) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.ingredients = ingredients;
    }

    public ProductDto(String name, String category, Double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public String getUpdatedByUserId() {
        return updatedByUserId;
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

    public List<IngredientDto> getIngredients() {
        return ingredients;
    }



}
