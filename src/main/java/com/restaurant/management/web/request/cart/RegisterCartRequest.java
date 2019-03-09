package com.restaurant.management.web.request.cart;

import javax.validation.constraints.NotNull;

public class RegisterCartRequest {

    @NotNull
    private Long phoneNumber;

    public Long getPhoneNumber() {
        return phoneNumber;
    }
}
