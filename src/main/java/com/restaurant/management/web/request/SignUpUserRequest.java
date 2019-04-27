package com.restaurant.management.web.request;

import javax.validation.constraints.*;

public final class SignUpUserRequest {
    @NotBlank(message = "name cannot be blank")
    @Size(min = 4, max = 40, message = "name must be between 4 - 40")
    private String name;

    @NotBlank
    @Size(min = 4, max = 40, message = "last name must be between 4 - 40")
    private String lastname;

    @NotBlank(message = "email cannot be blank")
    @Size(max = 40, message = "email max size is 40")
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 6, max = 100, message = "password must be between 6 - 100")
    private String password;

    public SignUpUserRequest() {
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}