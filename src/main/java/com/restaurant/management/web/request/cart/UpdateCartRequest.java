package com.restaurant.management.web.request.cart;


public class UpdateCartRequest {

    private Long phoneNumber;

    private String productName;

    private Integer quantity;

    public UpdateCartRequest() {
    }

    public UpdateCartRequest(Long phoneNumber,
                             String productName,
                             Integer quantity) {
        this.phoneNumber = phoneNumber;
        this.productName = productName;
        this.quantity = quantity;
    }

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
