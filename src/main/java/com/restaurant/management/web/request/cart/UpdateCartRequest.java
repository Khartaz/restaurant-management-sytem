package com.restaurant.management.web.request.cart;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class UpdateCartRequest {

    @NotBlank(message = "product name cannot be blank")
    private String productName;

    @NotNull(message = "quantity cannot be null")
    private Integer quantity;

    public UpdateCartRequest() {
    }

    public UpdateCartRequest(String productName,
                             Integer quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
