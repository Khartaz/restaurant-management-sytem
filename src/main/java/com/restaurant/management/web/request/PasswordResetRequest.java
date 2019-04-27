package com.restaurant.management.web.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public final class PasswordResetRequest {

    @NotBlank(message = "email cannot be blank")
    @Email(message = "email must be valid")
    private String email;

    public String getEmail() {
        return email;
    }

}
