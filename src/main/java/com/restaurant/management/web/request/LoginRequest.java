package com.restaurant.management.web.request;

import javax.validation.constraints.NotBlank;

public final class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}