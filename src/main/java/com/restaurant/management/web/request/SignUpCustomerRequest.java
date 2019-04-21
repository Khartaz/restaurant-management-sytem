package com.restaurant.management.web.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public final class SignUpCustomerRequest {

    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotBlank
    @Size(min = 4, max = 40)
    private String lastname;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotNull
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
