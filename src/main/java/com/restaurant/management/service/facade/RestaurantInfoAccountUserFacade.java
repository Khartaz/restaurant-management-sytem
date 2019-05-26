package com.restaurant.management.service.facade;

import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.service.RestaurantInfoService;
import com.restaurant.management.web.request.restaurant.RegisterRestaurantRequest;
import com.restaurant.management.web.response.restaurant.RegisterRestaurantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class RestaurantInfoAccountUserFacade {

    private RestaurantInfoService restaurantInfoService;
    private AccountUserMapper accountUserMapper;

    @Autowired
    public RestaurantInfoAccountUserFacade(RestaurantInfoService restaurantInfoService,
                                           AccountUserMapper accountUserMapper) {
        this.restaurantInfoService = restaurantInfoService;
        this.accountUserMapper = accountUserMapper;
    }

    public RegisterRestaurantResponse registerRestaurant(RegisterRestaurantRequest request) {

        return restaurantInfoService.registerRestaurant(request);
    }
}
