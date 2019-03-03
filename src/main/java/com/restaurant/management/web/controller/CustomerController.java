package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.mapper.CustomerMapper;
import com.restaurant.management.service.CustomerService;
import com.restaurant.management.web.request.SingUpCustomerRequest;
import com.restaurant.management.web.response.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @PostMapping("/signup")
    public @ResponseBody
    Resource<CustomerResponse> registerCustomer(@Valid @RequestBody SingUpCustomerRequest singUpCustomerRequest) {
        CustomerDto customerDto = customerService.createCustomer(singUpCustomerRequest);

        CustomerResponse response = customerMapper.mapToCustomerResponse(customerDto);

        Link link = linkTo(CustomerController.class).withSelfRel();
        return new Resource<>(response, link);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resources<CustomerResponse> getAllCustomers() {
        List<CustomerDto> customerDto = customerService.getAllCustomers();

        List<CustomerResponse> response = customerMapper.mapToCustomerResponseList(customerDto);

        Link link = linkTo(CustomerController.class).withSelfRel();
        return new Resources<>(response, link);
    }

}
