package com.restaurant.management.web.request.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public final class UpdateAccountInfo {

    @NotBlank(message = "name cannot be blank")
    @Size(min = 4, max = 40, message = "name must be between 4 - 40")
    private String name;

    @NotBlank(message = "lastName cannot be blank")
    @Size(min = 4, max = 40, message = "lastName must be between 4 - 40")
    private String lastName;

    private String phone;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }
}
