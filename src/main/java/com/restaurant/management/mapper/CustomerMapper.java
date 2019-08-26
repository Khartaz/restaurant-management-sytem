package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.Customer;
import com.restaurant.management.domain.ecommerce.CustomerOrdered;
import com.restaurant.management.domain.ecommerce.dto.CustomerDTO;
import com.restaurant.management.domain.ecommerce.dto.CustomerFormDTO;
import com.restaurant.management.web.response.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public final class CustomerMapper {
    private AddressMapper addressMapper;

    @Autowired
    public CustomerMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public Customer mapToCustomer(final CustomerDTO customerDto) {
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

    public CustomerOrdered mapToCustomerOrdered(final CustomerDTO customerDto) {
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

    public CustomerFormDTO mapToCustomerFormDTO(final Customer customer) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new CustomerFormDTO(
                formatter.format(customer.getCreatedAt()),
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

    public CustomerDTO mapToCustomerDto(final Customer customer) {
        return new CustomerDTO(
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

    public CustomerDTO mapToCustomerDto(final CustomerOrdered customer) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return new CustomerDTO(
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

    public CustomerResponse mapToCustomerResponse(final CustomerDTO customerDto) {
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

    public Page<CustomerDTO> mapToCustomerDtoPage(final Page<Customer> customers) {
        return customers.map(this::mapToCustomerDto);
    }

    public Page<CustomerFormDTO> mapToCustomerFormDTOPage(final Page<Customer> customers) {
        return customers.map(this::mapToCustomerFormDTO);
    }

    public Page<CustomerResponse> mapToCustomerResponsePage(final Page<CustomerDTO> customers) {
        return customers.map(this::mapToCustomerResponse);
    }

}
