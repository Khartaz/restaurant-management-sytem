package com.restaurant.management.web.request.cart;

import javax.validation.constraints.NotNull;

public class UpdateCartRequest {

//    @NotNull
    private Long phoneNumber;

//    @NotBlank
    private String productName;

//    @NotNull
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
