package com.restaurant.management.web.request.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public final class ProductRequest {

    private String uniqueId;

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "category cannot be blank")
    private String category;

    @NotNull(message = "price cannot be null")
    private double price;

    @NotNull(message = "ingredients cannot be null")
    private List<IngredientRequest> ingredients;

    public ProductRequest() {
    }

    public ProductRequest(String uniqueId, String name, String category,
                          double price, List<IngredientRequest> ingredients) {
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

    public double getPrice() {
        return price;
    }

    public List<IngredientRequest> getIngredients() {
        return ingredients;
    }
}
