package com.restaurant.management.service.facade;

import com.restaurant.management.domain.Customer;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.mapper.CustomerMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.impl.CustomerService;
import com.restaurant.management.web.request.SignUpCustomerRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public final class CustomerFacade {

    private CustomerMapper customerMapper;
    private CustomerService customerService;

    @Autowired
    public CustomerFacade(CustomerMapper customerMapper, CustomerService customerService) {
        this.customerMapper = customerMapper;
        this.customerService = customerService;
    }

    public CustomerDto createCustomer(@CurrentUser UserPrincipal currentUser, SignUpCustomerRequest request) {
        Customer customer = customerService.createCustomer(currentUser, request);

        return customerMapper.mapToCustomerDto(customer);
    }

    public Page<CustomerDto> getAllCustomers(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<Customer> customers = customerService.getAllCustomers(currentUser, pageable);

        return customerMapper.mapToCustomerDtoPage(customers);
    }

    public ApiResponse deleteCustomerById(@CurrentUser UserPrincipal currentUser, Long id) {
        return customerService.deleteCustomerById(currentUser, id);
    }

    public CustomerDto getCustomerById(@CurrentUser UserPrincipal currentUser,Long id) {
        Customer customer = customerService.getCustomerById(currentUser, id);

        return customerMapper.mapToCustomerDto(customer);
    }
}
