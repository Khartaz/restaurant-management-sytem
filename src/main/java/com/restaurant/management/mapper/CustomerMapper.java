package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.Customer;
import com.restaurant.management.domain.ecommerce.CustomerOrdered;
import com.restaurant.management.domain.ecommerce.dto.CustomerDto;
import com.restaurant.management.web.response.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class CustomerMapper {
    private AddressMapper addressMapper;

    @Autowired
    public CustomerMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public Customer mapToCustomer(final CustomerDto customerDto) {
        return new Customer(
                customerDto.getCreatedAt(),
                customerDto.getUpdatedAt(),
                customerDto.getCreatedByUserId(),
                customerDto.getUpdatedByUserId(),
                customerDto.getId(),
                customerDto.getName(),
                customerDto.getLastname(),
                customerDto.getEmail(),
                customerDto.getPhoneNumber(),
                addressMapper.mapToCustomerAddress(customerDto.getAddressDto())
        );
    }

    public CustomerOrdered mapToCustomerOrdered(final Customer customer) {
        return new CustomerOrdered(
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getCreatedByUserId(),
                customer.getUpdatedByUserId(),
                customer.getId(),
                customer.getName(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                addressMapper.mapToCustomerOrderedAddress(customer.getCustomerAddress())
        );
    }

    public CustomerOrdered mapToCustomerOrdered(final CustomerDto customerDto) {
        return new CustomerOrdered(
                customerDto.getCreatedAt(),
                customerDto.getUpdatedAt(),
                customerDto.getCreatedByUserId(),
                customerDto.getUpdatedByUserId(),
                customerDto.getId(),
                customerDto.getName(),
                customerDto.getLastname(),
                customerDto.getEmail(),
                customerDto.getPhoneNumber(),
                addressMapper.mapToCustomerOrderedAddress(customerDto.getAddressDto())
        );
    }

    public CustomerDto mapToCustomerDto(final Customer customer) {
        return new CustomerDto(
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getCreatedByUserId(),
                customer.getUpdatedByUserId(),
                customer.getId(),
                customer.getName(),
                customer.getLastname(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                addressMapper.mapToAddressDto(customer.getCustomerAddress())
        );
    }

    public CustomerDto mapToCustomerDto(final CustomerOrdered customer) {
        return new CustomerDto(
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getCreatedByUserId(),
                customer.getUpdatedByUserId(),
                customer.getId(),
                customer.getName(),
                customer.getLastname(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                addressMapper.mapToAddressDto(customer.getCustomerOrderedAddress())
        );
    }

    public CustomerResponse mapToCustomerResponse(final CustomerDto customerDto) {
        return new CustomerResponse(
                customerDto.getCreatedAt(),
                customerDto.getUpdatedAt(),
                customerDto.getCreatedByUserId(),
                customerDto.getUpdatedByUserId(),
                customerDto.getId(),
                customerDto.getName(),
                customerDto.getLastname(),
                customerDto.getEmail(),
                customerDto.getPhoneNumber(),
                addressMapper.mapToAddressResponse(customerDto.getAddressDto())
        );
    }

    public List<CustomerDto> mapToCustomerDtoList(final List<Customer> customers) {
        return customers.stream()
                .map(this::mapToCustomerDto)
                .collect(Collectors.toList());
    }

    public Page<CustomerDto> mapToCustomerDtoPage(final Page<Customer> customers) {
        return customers.map(this::mapToCustomerDto);
    }

    public List<CustomerResponse> mapToCustomerResponseList(final List<CustomerDto> customers) {
        return customers.stream()
                .map(this::mapToCustomerResponse)
                .collect(Collectors.toList());
    }

    public Page<CustomerResponse> mapToCustomerResponsePage(final Page<CustomerDto> customers) {
        return customers.map(this::mapToCustomerResponse);
    }

    public List<Customer> mapToCustomerList(final List<CustomerDto> customers) {
        return customers.stream()
                .map(this::mapToCustomer)
                .collect(Collectors.toList());
    }
}
