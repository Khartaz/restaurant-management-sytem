package com.restaurant.management.web.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public final class SignUpCustomerRequest {

    @NotBlank(message = "name cannot be blank")
    @Size(min = 4, max = 40, message = "name must be between 4 - 40")
    private String name;

    @NotBlank(message = "lastname cannot be blank")
    @Size(min = 4, max = 40, message = "lastname must be between 4 - 40")
    private String lastname;

    @NotBlank(message = "email cannot be blank")
    @Size(max = 40, message = "email max size is 40")
    @Email(message = "email must be valid")
    private String email;

    @NotNull(message = "phoneNumber cannot be null")
    private Long phoneNumber;

    public SignUpCustomerRequest() {
    }

    public SignUpCustomerRequest(String name, String lastname, String email, Long phoneNumber) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
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

    public Long getPhoneNumber() {
        return phoneNumber;
    }
}
