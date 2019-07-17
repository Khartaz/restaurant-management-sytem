package com.restaurant.management.service;

import com.restaurant.management.domain.ecommerce.RestaurantInfo;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.request.restaurant.RegisterRestaurantRequest;
import com.restaurant.management.web.response.restaurant.RegisterRestaurant;

public interface RestaurantInfoService {

    RegisterRestaurant registerRestaurant(RegisterRestaurantRequest registerRestaurantRequest);

    RestaurantInfo getRestaurantInfoById(@CurrentUser UserPrincipal currentUser);
}
