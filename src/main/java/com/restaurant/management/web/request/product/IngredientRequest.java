package com.restaurant.management.web.request.product;

import javax.validation.constraints.NotBlank;

public final class IngredientRequest {

    @NotBlank
    private String name;

    public IngredientRequest() {
    }

    public IngredientRequest(@NotBlank String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
