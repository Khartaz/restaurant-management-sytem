package com.restaurant.management.web.controller;

import com.restaurant.management.service.CustomerService;
import com.restaurant.management.web.request.SingUpCustomerRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody SingUpCustomerRequest singUpCustomerRequest) {
        customerService.createCustomer(singUpCustomerRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/customers/{name}")
                .buildAndExpand(singUpCustomerRequest.getName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Customer registered successfully"));
    }
}
