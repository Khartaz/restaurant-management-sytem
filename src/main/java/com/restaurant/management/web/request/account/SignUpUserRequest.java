package com.restaurant.management.web.request.account;

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
    @Size(min = 8, max = 100, message = "password must be between 8 - 100")
    private String password;

    @NotNull(message = "phone number cannot be null")
//    @Size(min = 4, max = 15, message = "phone number cannot be null")
    private Long phoneNumber;

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

    public Long getPhoneNumber() {
        return phoneNumber;
    }
}