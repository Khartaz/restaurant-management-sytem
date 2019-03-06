package com.restaurant.management.web.request.product;

import javax.validation.constraints.NotBlank;

public class IngredientRequest {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }
}
