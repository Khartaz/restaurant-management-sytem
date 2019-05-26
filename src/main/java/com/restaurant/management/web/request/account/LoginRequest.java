package com.restaurant.management.web.request.account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public final class LoginRequest {
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 6, max = 100, message = "password must be between 6 - 100")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}