package com.restaurant.management.web.request.product;

import javax.validation.constraints.NotBlank;

public final class IngredientRequest {

    @NotBlank(message = "ingredient cannot be blank")
    private String name;

    public IngredientRequest() {
    }

    public IngredientRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
