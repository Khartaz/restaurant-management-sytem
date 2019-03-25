package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.mapper.CustomerMapper;
import com.restaurant.management.service.facade.CustomerFacade;
import com.restaurant.management.web.request.SignUpCustomerRequest;
import com.restaurant.management.web.response.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerFacade customerFacade;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerController(CustomerFacade customerFacade, CustomerMapper customerMapper) {
        this.customerFacade = customerFacade;
        this.customerMapper = customerMapper;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CustomerResponse> registerCustomer(@Valid @RequestBody SignUpCustomerRequest signUpCustomerRequest) {
        CustomerDto customerDto = customerFacade.createCustomer(signUpCustomerRequest);

        CustomerResponse response = customerMapper.mapToCustomerResponse(customerDto);

        Link link = linkTo(CustomerController.class).slash(response.getId()).withSelfRel();
        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerFacade.deleteCustomerById(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resources<CustomerResponse> getAllCustomers() {
        List<CustomerDto> customerDto = customerFacade.getAllCustomers();

        List<CustomerResponse> response = customerMapper.mapToCustomerResponseList(customerDto);

        Link link = linkTo(CustomerController.class).withSelfRel();
        return new Resources<>(response, link);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CustomerResponse> showCustomer(@PathVariable Long id) {
        CustomerDto customerDto = customerFacade.getCustomerById(id);

        CustomerResponse response = customerMapper.mapToCustomerResponse(customerDto);

        Link link = linkTo(CustomerController.class).slash(response.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

}
