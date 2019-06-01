package com.restaurant.management.service;

import com.restaurant.management.web.request.restaurant.RegisterRestaurantRequest;
import com.restaurant.management.web.response.restaurant.RegisterRestaurantResponse;

public interface RestaurantInfoService {

    RegisterRestaurantResponse registerRestaurant(RegisterRestaurantRequest registerRestaurantRequest);

}
