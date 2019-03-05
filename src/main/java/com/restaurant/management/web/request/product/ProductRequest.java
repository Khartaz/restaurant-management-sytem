package com.restaurant.management.web.request.product;

import com.restaurant.management.domain.Ingredient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductRequest {

//    @NotBlank
    private String uniqueId;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotNull
    private double price;

    @NotNull
    private List<Ingredient> ingredients;

    public String getUniqueId() {
        return uniqueId;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
