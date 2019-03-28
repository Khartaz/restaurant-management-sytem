package com.restaurant.management.service.facade;

import com.restaurant.management.domain.Customer;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.mapper.CustomerMapper;
import com.restaurant.management.service.CustomerService;
import com.restaurant.management.web.request.SignUpCustomerRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerFacade {

    private CustomerMapper customerMapper;
    private CustomerService customerService;

    @Autowired
    public CustomerFacade(CustomerMapper customerMapper, CustomerService customerService) {
        this.customerMapper = customerMapper;
        this.customerService = customerService;
    }

    public CustomerDto createCustomer(SignUpCustomerRequest request) {
        Customer customer = customerService.createCustomer(request);

        return customerMapper.mapToCustomerDto(customer);
    }

    public Page<CustomerDto> getAllCustomers(Pageable pageable) {
        Page<Customer> customers = customerService.getAllCustomers(pageable);

        return customerMapper.mapToCustomerDtoPage(customers);
    }

    public ApiResponse deleteCustomerById(Long id) {
        return customerService.deleteCustomerById(id);
    }

    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerService.getCustomerById(id);

        return customerMapper.mapToCustomerDto(customer);
    }
}
