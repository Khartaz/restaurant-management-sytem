package com.restaurant.management.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public final class UpdateAccountNameOrLastname {

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotBlank
    @Size(min = 4, max = 40)
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
