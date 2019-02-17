package com.restaurant.management.web.response;

public class IngredientResponse {

    private String name;

    public IngredientResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
