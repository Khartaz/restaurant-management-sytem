package com.restaurant.management.web.response;

public class IngredientResponse {
    private Long id;

    private String name;

    public IngredientResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
