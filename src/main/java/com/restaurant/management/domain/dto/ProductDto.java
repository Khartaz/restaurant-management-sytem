package com.restaurant.management.domain.dto;

import java.time.Instant;
import java.util.List;

public class ProductDto {

    private Long id;
    private String uniqueId;
    private String name;
    private String category;
    private Double price;
    private Instant createdAt;
    private List<IngredientDto> ingredients;

    public ProductDto(Long id, String uniqueId, String name, String category, Double price,
                      Instant createdAt, List<IngredientDto> ingredients) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.createdAt = createdAt;
        this.ingredients = ingredients;
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

    public Instant getCreatedAt() {
        return createdAt;
    }
}
