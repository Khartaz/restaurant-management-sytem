package com.restaurant.management.web.request.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public final class ProductRequest {

    @NotNull(message = "Id cannot be null")
    private Long id;

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "category cannot be blank")
    private String category;

    @NotNull(message = "price cannot be null")
    private double price;

    public ProductRequest() {
    }

    public ProductRequest(Long id, String name, String category,
                          Double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Long getId() {
        return id;
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
