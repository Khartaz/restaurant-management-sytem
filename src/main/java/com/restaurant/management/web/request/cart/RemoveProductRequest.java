package com.restaurant.management.web.request.cart;

import javax.validation.constraints.NotBlank;

public final class RemoveProductRequest {

    @NotBlank(message = "product name cannot be blank")
    private String productName;

    public RemoveProductRequest() {
    }

    public RemoveProductRequest(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}
