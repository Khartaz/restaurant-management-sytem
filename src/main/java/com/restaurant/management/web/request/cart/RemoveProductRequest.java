package com.restaurant.management.web.request.cart;

public class RemoveProductRequest {

    private Long phoneNumber;

    private String productName;

    public RemoveProductRequest() {
    }

    public RemoveProductRequest(Long phoneNumber, String productName) {
        this.phoneNumber = phoneNumber;
        this.productName = productName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public String getProductName() {
        return productName;
    }
}
