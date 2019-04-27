package com.restaurant.management.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public final class PasswordReset {
    @NotBlank(message = "password cannot be blank")
    @Size(min = 6, max = 100, message = "password must be between 6 - 100")
    private String password;

    @NotBlank(message = "confirmPassword cannot be blank")
    @Size(min = 6, max = 100, message = "confirmPassword must be between 6 - 100")
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

}