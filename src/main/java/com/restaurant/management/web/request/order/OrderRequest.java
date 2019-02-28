package com.restaurant.management.web.request.order;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrderRequest {

    @NotNull
    private Long phoneNumber;

    @NotBlank
    private String productName;

    @NotNull
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
