package com.restaurant.management.web.request.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class RegisterProductRequest {

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "category cannot be blank")
    private String category;

    @NotNull(message = "price cannot be null")
    private Double price;

    public RegisterProductRequest() {
    }

    public RegisterProductRequest(String name, String category, Double price) {
        this.name = name;
        this.category = category;
        this.price = price;
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

}
