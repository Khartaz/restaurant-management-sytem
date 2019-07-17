package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.CustomerAddress;
import com.restaurant.management.domain.ecommerce.RestaurantAddress;
import com.restaurant.management.domain.archive.CustomerArchiveAddress;
import com.restaurant.management.domain.dto.AddressDto;
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

    public RestaurantAddress mapToRestaurantAddress(final AddressDto addressDto) {
        return new RestaurantAddress(
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

    public AddressDto mapToAddressDto(final RestaurantAddress restaurantAddress) {
        return new AddressDto(
                restaurantAddress.getCreatedAt(),
                restaurantAddress.getUpdatedAt(),
                restaurantAddress.getCreatedByUserId(),
                restaurantAddress.getUpdatedByUserId(),
                restaurantAddress.getId(),
                restaurantAddress.getStreetAndNumber(),
                restaurantAddress.getPostCode(),
                restaurantAddress.getCity(),
                restaurantAddress.getCountry()
        );
    }

    public AddressDto mapToAddressDto(final CustomerArchiveAddress customerArchiveAddress) {
        return new AddressDto(
                customerArchiveAddress.getCreatedAt(),
                customerArchiveAddress.getUpdatedAt(),
                customerArchiveAddress.getCreatedByUserId(),
                customerArchiveAddress.getUpdatedByUserId(),
                customerArchiveAddress.getId(),
                customerArchiveAddress.getStreetAndNumber(),
                customerArchiveAddress.getPostCode(),
                customerArchiveAddress.getCity(),
                customerArchiveAddress.getCountry()
        );
    }

    public AddressResponse mapToAddressResponse(final RestaurantAddress restaurantAddress) {
        return new AddressResponse(
                restaurantAddress.getCreatedAt(),
                restaurantAddress.getUpdatedAt(),
                restaurantAddress.getCreatedByUserId(),
                restaurantAddress.getUpdatedByUserId(),
                restaurantAddress.getId(),
                restaurantAddress.getStreetAndNumber(),
                restaurantAddress.getPostCode(),
                restaurantAddress.getCity(),
                restaurantAddress.getCountry()
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

    public CustomerArchiveAddress mapToCustomerArchiveAddress(final AddressDto addressDto) {
        return new CustomerArchiveAddress(
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

    public CustomerArchiveAddress mapToCustomerArchiveAddress(final CustomerAddress customerAddress) {
        return new CustomerArchiveAddress(
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
