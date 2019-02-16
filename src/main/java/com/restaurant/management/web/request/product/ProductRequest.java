package com.restaurant.management.web.request.product;

import javax.validation.constraints.NotBlank;

public class ProductRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotBlank
    private double price;

    @NotBlank
    private String ingredients;

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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
