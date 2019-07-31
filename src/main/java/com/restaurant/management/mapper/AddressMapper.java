package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.CustomerAddress;
import com.restaurant.management.domain.ecommerce.CompanyAddress;
import com.restaurant.management.domain.ecommerce.CustomerOrderedAddress;
import com.restaurant.management.domain.ecommerce.dto.AddressDto;
import com.restaurant.management.web.response.AddressResponse;
import org.springframework.stereotype.Component;

@Component
public final class AddressMapper {

    public CustomerAddress mapToCustomerAddress(final AddressDto addressDto) {
        return new CustomerAddress(
                addressDto.getCreatedAt(),
                addressDto.getUpdatedAt(),
                addressDto.getCreatedByUserId(),
                addressDto.getUpdatedByUserId(),
                addressDto.getId(),
                addressDto.getStreetAndNumber(),
                addressDto.getPostCode(),
                addressDto.getCity(),
                addressDto.getCountry()
        );
    }

    public CompanyAddress mapToRestaurantAddress(final AddressDto addressDto) {
        return new CompanyAddress(
                addressDto.getCreatedAt(),
                addressDto.getUpdatedAt(),
                addressDto.getCreatedByUserId(),
                addressDto.getUpdatedByUserId(),
                addressDto.getId(),
                addressDto.getStreetAndNumber(),
                addressDto.getPostCode(),
                addressDto.getCity(),
                addressDto.getCountry()
        );
    }

    public AddressDto mapToAddressDto(final CustomerAddress customerAddress) {
        return new AddressDto(
                customerAddress.getCreatedAt(),
                customerAddress.getUpdatedAt(),
                customerAddress.getCreatedByUserId(),
                customerAddress.getUpdatedByUserId(),
                customerAddress.getId(),
                customerAddress.getStreetAndNumber(),
                customerAddress.getPostCode(),
                customerAddress.getCity(),
                customerAddress.getCountry()
        );
    }

    public AddressDto mapToAddressDto(final CompanyAddress companyAddress) {
        return new AddressDto(
                companyAddress.getCreatedAt(),
                companyAddress.getUpdatedAt(),
                companyAddress.getCreatedByUserId(),
                companyAddress.getUpdatedByUserId(),
                companyAddress.getId(),
                companyAddress.getStreetAndNumber(),
                companyAddress.getPostCode(),
                companyAddress.getCity(),
                companyAddress.getCountry()
        );
    }

    public AddressDto mapToAddressDto(final CustomerOrderedAddress customerOrderedAddress) {
        return new AddressDto(
                customerOrderedAddress.getCreatedAt(),
                customerOrderedAddress.getUpdatedAt(),
                customerOrderedAddress.getCreatedByUserId(),
                customerOrderedAddress.getUpdatedByUserId(),
                customerOrderedAddress.getId(),
                customerOrderedAddress.getStreetAndNumber(),
                customerOrderedAddress.getPostCode(),
                customerOrderedAddress.getCity(),
                customerOrderedAddress.getCountry()
        );
    }

    public AddressResponse mapToAddressResponse(final CompanyAddress companyAddress) {
        return new AddressResponse(
                companyAddress.getCreatedAt(),
                companyAddress.getUpdatedAt(),
                companyAddress.getCreatedByUserId(),
                companyAddress.getUpdatedByUserId(),
                companyAddress.getId(),
                companyAddress.getStreetAndNumber(),
                companyAddress.getPostCode(),
                companyAddress.getCity(),
                companyAddress.getCountry()
        );
    }

    public AddressDto mapToAddressResponse(final CustomerAddress customerAddress) {
        return new AddressDto(
                customerAddress.getCreatedAt(),
                customerAddress.getUpdatedAt(),
                customerAddress.getCreatedByUserId(),
                customerAddress.getUpdatedByUserId(),
                customerAddress.getId(),
                customerAddress.getStreetAndNumber(),
                customerAddress.getPostCode(),
                customerAddress.getCity(),
                customerAddress.getCountry()
        );
    }

    public AddressResponse mapToAddressResponse(final AddressDto addressDto) {
        return new AddressResponse(
                addressDto.getCreatedAt(),
                addressDto.getUpdatedAt(),
                addressDto.getCreatedByUserId(),
                addressDto.getUpdatedByUserId(),
                addressDto.getId(),
                addressDto.getStreetAndNumber(),
                addressDto.getPostCode(),
                addressDto.getCity(),
                addressDto.getCountry()
        );
    }

    public CustomerOrderedAddress mapToCustomerOrderedAddress(final AddressDto addressDto) {
        return new CustomerOrderedAddress(
                addressDto.getCreatedAt(),
                addressDto.getUpdatedAt(),
                addressDto.getCreatedByUserId(),
                addressDto.getUpdatedByUserId(),
                addressDto.getId(),
                addressDto.getStreetAndNumber(),
                addressDto.getPostCode(),
                addressDto.getCity(),
                addressDto.getCountry()
        );
    }

    public CustomerOrderedAddress mapToCustomerOrderedAddress(final CustomerAddress customerAddress) {
        return new CustomerOrderedAddress(
                customerAddress.getCreatedAt(),
                customerAddress.getUpdatedAt(),
                customerAddress.getCreatedByUserId(),
                customerAddress.getUpdatedByUserId(),
                customerAddress.getId(),
                customerAddress.getStreetAndNumber(),
                customerAddress.getPostCode(),
                customerAddress.getCity(),
                customerAddress.getCountry()
        );
    }

}
