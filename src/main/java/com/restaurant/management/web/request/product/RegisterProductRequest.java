package com.restaurant.management.web.request.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public final class RegisterProductRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotNull
    private double price;

    @NotNull
    private List<IngredientRequest> ingredients;

    public RegisterProductRequest() {
    }

    public RegisterProductRequest(String name, String category,
                                  double price,List<IngredientRequest> ingredients) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public List<IngredientRequest> getIngredients() {
        return ingredients;
    }
}
