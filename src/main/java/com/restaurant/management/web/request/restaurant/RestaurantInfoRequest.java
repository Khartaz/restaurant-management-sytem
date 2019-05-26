package com.restaurant.management.web.request.restaurant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public final class RestaurantInfoRequest {

    @NotBlank(message = "restaurant name cannot be blank")
    @Size(min = 8, max = 40, message = "restaurant name must be between 8 - 60")
    private String restaurantName;

    private RestaurantAddressRequest restaurantAddressRequest;

    public String getRestaurantName() {
        return restaurantName;
    }

    public RestaurantAddressRequest getRestaurantAddressRequest() {
        return restaurantAddressRequest;
    }
}
