package com.restaurant.management.web.request.restaurant;

import com.restaurant.management.web.request.account.SignUpUserRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public final class RegisterRestaurantRequest {

    @NotNull(message = "user info cannot be null")
    private SignUpUserRequest signUpUserRequest;

    @NotNull(message = "restaurant info cannot be null")
    private RestaurantInfoRequest restaurantInfoRequest;

    public RegisterRestaurantRequest() {
    }

    public SignUpUserRequest getSignUpUserRequest() {
        return signUpUserRequest;
    }

    public RestaurantInfoRequest getRestaurantInfoRequest() {
        return restaurantInfoRequest;
    }
}
