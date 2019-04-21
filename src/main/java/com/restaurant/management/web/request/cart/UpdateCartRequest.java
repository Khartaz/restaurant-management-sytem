package com.restaurant.management.web.request.cart;


public final class UpdateCartRequest {

    private String productName;

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
