package com.restaurant.management.web.response;

import java.time.Instant;
import java.util.List;

public class ProductResponse {

    private String name;
    private String category;
    private Double price;
    private List<IngredientResponse> ingredients;

    public ProductResponse(String name, String category, Double price, List<IngredientResponse> ingredients) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.ingredients = ingredients;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<IngredientResponse> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientResponse> ingredients) {
        this.ingredients = ingredients;
    }

}
