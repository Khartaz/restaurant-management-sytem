package com.restaurant.management.web.request.cart;

import javax.validation.constraints.NotNull;

public class RegisterCartRequest {

    @NotNull
    private Long phoneNumber;

    public RegisterCartRequest() {
    }

    public RegisterCartRequest(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }
}
