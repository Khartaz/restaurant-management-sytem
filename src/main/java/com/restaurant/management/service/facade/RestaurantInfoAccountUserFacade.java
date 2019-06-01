package com.restaurant.management.service.facade;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.RestaurantInfo;
import com.restaurant.management.mapper.RestaurantInfoMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.service.RestaurantInfoService;
import com.restaurant.management.web.request.restaurant.RegisterRestaurantRequest;
import com.restaurant.management.web.response.restaurant.RegisterRestaurantResponse;
import com.restaurant.management.web.response.restaurant.RestaurantInfoResponse;
import com.restaurant.management.web.response.user.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class RestaurantInfoAccountUserFacade {

    private RestaurantInfoService restaurantInfoService;
    private AccountUserService accountUserService;
    private RestaurantInfoMapper restaurantInfoMapper;

    @Autowired
    public RestaurantInfoAccountUserFacade(RestaurantInfoService restaurantInfoService,
                                           AccountUserService accountUserService,
                                           RestaurantInfoMapper restaurantInfoMapper) {
        this.restaurantInfoService = restaurantInfoService;
        this.accountUserService = accountUserService;
        this.restaurantInfoMapper = restaurantInfoMapper;
    }

    public RegisterRestaurantResponse registerRestaurant(RegisterRestaurantRequest request) {

        return restaurantInfoService.registerRestaurant(request);
    }

    public UserSummary getUserSummary(@CurrentUser UserPrincipal currentUser) {
        RestaurantInfo restaurantInfo = restaurantInfoService.getRestaurantInfoById(currentUser);

        RestaurantInfoResponse restaurantInfoResponse = restaurantInfoMapper.mapToRestaurantInfoResponse(restaurantInfo);

        AccountUser accountUser = accountUserService.getUserById(currentUser.getId());

        return new UserSummary(
                accountUser.getId(),
                accountUser.getName(),
                accountUser.getLastname(),
                accountUser.getEmail(),
                accountUser.getPhoneNumber(),
                accountUser.getRoles(),
                restaurantInfoResponse
        );
    }

}
