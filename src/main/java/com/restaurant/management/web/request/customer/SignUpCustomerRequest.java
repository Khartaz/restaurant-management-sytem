package com.restaurant.management.web.request.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public final class SignUpCustomerRequest {

    @NotBlank(message = "name cannot be blank")
    @Size(min = 4, max = 40, message = "name must be between 4 - 40")
    private String name;

    @NotBlank(message = "lastName cannot be blank")
    @Size(min = 4, max = 40, message = "lastName must be between 4 - 40")
    private String lastName;

    @NotBlank(message = "email cannot be blank")
    @Size(max = 40, message = "email max size is 40")
    @Email(message = "email must be valid")
    private String email;

    private String phone;

    @NotNull(message = "customer address cannot be null")
    private CustomerAddressRequest customerAddressRequest;

    public SignUpCustomerRequest() {
    }

    public SignUpCustomerRequest(String name, String lastName, String email, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public CustomerAddressRequest getCustomerAddressRequest() {
        return customerAddressRequest;
    }
}
