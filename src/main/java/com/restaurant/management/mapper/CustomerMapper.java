package com.restaurant.management.mapper;

import com.restaurant.management.domain.Customer;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.web.response.CustomerResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
                customerDto.getEmail(),
                customerDto.getPhoneNumber()
        );
    }

    public List<CustomerDto> mapToCustomerDtoList(final List<Customer> customers) {
        return customers.stream()
                .map(v -> new CustomerDto(
                        v.getId(),
                        v.getName(),
                        v.getLastname(),
                        v.getEmail(),
                        v.getPhoneNumber()
                        ))
                .collect(Collectors.toList());
    }

    public List<CustomerResponse> mapToCustomerResponseList(final List<CustomerDto> customers) {
        return customers.stream()
                .map(v -> new CustomerResponse(
                        v.getName(),
                        v.getLastname(),
                        v.getEmail(),
                        v.getPhoneNumber()
                ))
                .collect(Collectors.toList());
    }
}
