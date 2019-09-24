package com.restaurant.management.mapper.ecommerce;

import com.restaurant.management.domain.ecommerce.AccountUserAddress;
import com.restaurant.management.domain.ecommerce.CustomerAddress;
import com.restaurant.management.domain.ecommerce.CompanyAddress;
import com.restaurant.management.domain.ecommerce.CustomerOrderedAddress;
import com.restaurant.management.domain.ecommerce.dto.AddressDTO;
import com.restaurant.management.web.response.AddressResponse;
import org.springframework.stereotype.Component;

@Component
public final class AddressMapper {

    public AddressDTO mapToAddressDTO(final AccountUserAddress accountUserAddress) {
        return new AddressDTO(
                accountUserAddress.getCreatedAt(),
                accountUserAddress.getUpdatedAt(),
                accountUserAddress.getCreatedByUserId(),
                accountUserAddress.getUpdatedByUserId(),
                accountUserAddress.getId(),
                accountUserAddress.getStreetAndNumber(),
                accountUserAddress.getPostCode(),
                accountUserAddress.getCity(),
                accountUserAddress.getCountry()
        );
    }

    public CustomerAddress mapToCustomerAddress(final AddressDTO addressDto) {
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

    public CompanyAddress mapToRestaurantAddress(final AddressDTO addressDto) {
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

    public AddressDTO mapToAddressDto(final CustomerAddress customerAddress) {
        return new AddressDTO(
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

    public AddressDTO mapToAddressDto(final CompanyAddress companyAddress) {
        return new AddressDTO(
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

    public AddressDTO mapToAddressDto(final CustomerOrderedAddress customerOrderedAddress) {
        return new AddressDTO(
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

    public AddressDTO mapToAddressResponse(final CustomerAddress customerAddress) {
        return new AddressDTO(
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

    public AddressResponse mapToAddressResponse(final AddressDTO addressDto) {
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

    public CustomerOrderedAddress mapToCustomerOrderedAddress(final AddressDTO addressDto) {
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
