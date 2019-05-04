package com.restaurant.management.domain.dto;

import java.util.List;

public final class ProductDto {

    private Long createdAt;
    private Long updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long id;
    private String uniqueId;
    private String name;
    private String category;
    private Double price;
    private List<IngredientDto> ingredients;

    public ProductDto() {
    }

    public ProductDto(Long createdAt, Long updatedAt, String createdBy, String updatedBy,
                      Long id, String uniqueId, String name,
                      String category, Double price, List<IngredientDto> ingredients) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.ingredients = ingredients;
    }

    public ProductDto(String uniqueId, String name, String category,
                      Double price, List<IngredientDto> ingredients) {
        this.uniqueId = uniqueId;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Long getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
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
