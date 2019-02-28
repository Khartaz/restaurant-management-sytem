package com.restaurant.management.web.response;

import java.util.List;

public class ProductResponse {

    private String uniqueId;
    private String name;
    private String category;
    private Double price;
    private List<IngredientResponse> ingredients;

    public ProductResponse(String uniqueId, String name, String category, Double price, List<IngredientResponse> ingredients) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public List<IngredientResponse> getIngredients() {
        return ingredients;
    }
}
