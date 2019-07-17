package com.restaurant.management.service.facade;


import com.restaurant.management.domain.ecommerce.RestaurantInfo;
import com.restaurant.management.domain.dto.RestaurantInfoDto;
import com.restaurant.management.mapper.RestaurantInfoMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.RestaurantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class RestaurantInfoFacade {
    private RestaurantInfoService restaurantInfoService;
    private RestaurantInfoMapper restaurantInfoMapper;

    @Autowired
    public RestaurantInfoFacade(RestaurantInfoService restaurantInfoService,
                                RestaurantInfoMapper restaurantInfoMapper) {
        this.restaurantInfoService = restaurantInfoService;
        this.restaurantInfoMapper = restaurantInfoMapper;
    }

    public RestaurantInfoDto getRestaurantInfo(@CurrentUser UserPrincipal currentUser) {
        RestaurantInfo restaurantInfo = restaurantInfoService.getRestaurantInfoById(currentUser);

        return restaurantInfoMapper.mapToRestaurantInfoDto(restaurantInfo);
    }
}
