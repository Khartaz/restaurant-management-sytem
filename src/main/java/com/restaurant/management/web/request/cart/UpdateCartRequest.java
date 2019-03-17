package com.restaurant.management.web.request.cart;


public class UpdateCartRequest {

    private Long phoneNumber;

    private String productName;

    private Integer quantity;

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
