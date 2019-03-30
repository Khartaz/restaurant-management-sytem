package com.restaurant.management.web.request.cart;

public class RemoveProductRequest {

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
