package com.restaurant.management.mapper;

import com.restaurant.management.domain.RestaurantInfo;
import com.restaurant.management.web.response.restaurant.RestaurantInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class RestaurantInfoMapper {

    private AddressMapper addressMapper;

    @Autowired
    public void setAddressMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public RestaurantInfoResponse mapToRestaurantInfoResponse(final RestaurantInfo restaurantInfo) {

        return new RestaurantInfoResponse(
                restaurantInfo.getCreatedAt(),
                restaurantInfo.getUpdatedAt(),
                restaurantInfo.getCreatedByUserId(),
                restaurantInfo.getUpdatedByUserId(),
                restaurantInfo.getId(),
                restaurantInfo.getName(),
                addressMapper.mapToAddressResponse(restaurantInfo.getRestaurantAddress())
        );
    }
}
