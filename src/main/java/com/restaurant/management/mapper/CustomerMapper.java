package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.Customer;
import com.restaurant.management.domain.ecommerce.CustomerOrdered;
import com.restaurant.management.domain.ecommerce.dto.CustomerDto;
import com.restaurant.management.domain.ecommerce.dto.CustomerFormDTO;
import com.restaurant.management.web.response.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
                customerDto.getLastName(),
                customerDto.getEmail(),
                customerDto.getPhone(),
                "temp job title",
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
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
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
                customerDto.getLastName(),
                customerDto.getEmail(),
                customerDto.getPhone(),
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
                customer.getLastName(),
                customer.getPhone(),
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
                customer.getLastName(),
                customer.getPhone(),
                customer.getEmail(),
                addressMapper.mapToAddressDto(customer.getCustomerOrderedAddress())
        );
    }

    public CustomerFormDTO mapToCustomerFormDTO(final Customer customer) {
        return new CustomerFormDTO(
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getCreatedByUserId(),
                customer.getUpdatedByUserId(),
                customer.getId(),
                customer.getName(),
                customer.getLastName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getCustomerAddress().getStreetAndNumber(),
                customer.getCustomerAddress().getPostCode(),
                customer.getCustomerAddress().getCity(),
                customer.getCustomerAddress().getCountry()
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
                customerDto.getLastName(),
                customerDto.getEmail(),
                customerDto.getPhone(),
                addressMapper.mapToAddressResponse(customerDto.getAddressDto())
        );
    }

    public Page<CustomerDto> mapToCustomerDtoPage(final Page<Customer> customers) {
        return customers.map(this::mapToCustomerDto);
    }

    public Page<CustomerResponse> mapToCustomerResponsePage(final Page<CustomerDto> customers) {
        return customers.map(this::mapToCustomerResponse);
    }

    public Page<CustomerFormDTO> mapToCustomerFormDTOPage(final Page<Customer> customers) {
        return customers.map(this::mapToCustomerFormDTO);
    }
}
