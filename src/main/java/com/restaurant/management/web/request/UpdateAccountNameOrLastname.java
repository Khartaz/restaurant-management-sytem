package com.restaurant.management.web.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateAccountNameOrLastname {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotBlank
    @Size(min = 4, max = 40)
    private String lastname;


    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

}
