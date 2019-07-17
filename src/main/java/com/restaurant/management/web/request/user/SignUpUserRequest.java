package com.restaurant.management.web.request.user;

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

    //    @Size(min = 4, max = 15, message = "phone number cannot be null")
    private String phoneNumber;

    public SignUpUserRequest() {
    }

    public SignUpUserRequest(String name, String lastname,  String email,
                             String password, String phoneNumber) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
}