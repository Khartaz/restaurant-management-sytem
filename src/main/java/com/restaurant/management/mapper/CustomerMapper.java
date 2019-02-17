package com.restaurant.management.mapper;

import com.restaurant.management.domain.Customer;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.web.response.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer mapToCustomer(final CustomerDto customerDto) {
        return new Customer(
                customerDto.getId(),
                customerDto.getName(),
                customerDto.getLastname(),
                customerDto.getEmail(),
                customerDto.getPhoneNumber()
        );
    }

    public CustomerDto mapToCustomerDto(final Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getPhoneNumber());
    }

    public CustomerResponse mapToCustomerResponse(final CustomerDto customerDto) {
        return new CustomerResponse(
                customerDto.getName(),
                customerDto.getLastname(),
                customerDto.getPhoneNumber(),
                customerDto.getEmail()
        );
    }
}
