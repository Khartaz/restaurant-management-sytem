package com.restaurant.management.web.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public final class UpdateAccountNameOrLastname {

    @NotBlank(message = "email cannot be blank")
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "name cannot be blank")
    @Size(min = 4, max = 40, message = "name must be between 4 - 40")
    private String name;

    @NotBlank(message = "lastname cannot be blank")
    @Size(min = 4, max = 40, message = "lastname must be between 4 - 40")
    private String lastname;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

}
