package com.restaurant.management.web.request.product;

import com.restaurant.management.domain.Ingredient;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class ProductRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotBlank
    private double price;

    private List<Ingredient> ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
