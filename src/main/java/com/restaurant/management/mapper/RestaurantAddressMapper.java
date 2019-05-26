package com.restaurant.management.mapper;

import com.restaurant.management.domain.RestaurantAddress;
import com.restaurant.management.web.response.restaurant.RestaurantAddressResponse;
import org.springframework.stereotype.Component;

@Component
public final class RestaurantAddressMapper {

    public RestaurantAddressResponse mapToRestaurantAddressResponse(final RestaurantAddress restaurantAddress) {
        return new RestaurantAddressResponse(
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
}
