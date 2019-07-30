package com.restaurant.management.domain.ecommerce.dto;

public final class IngredientDto {

    private Long id;

    private String name;

    public IngredientDto() {
    }

    public IngredientDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public IngredientDto(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
