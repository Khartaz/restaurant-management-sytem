package com.restaurant.management.web.response.restaurant;

import com.restaurant.management.web.response.user.AccountUserResponse;

public final class RegisterRestaurantResponse {

    private AccountUserResponse accountUserResponse;

    private RestaurantInfoResponse restaurantInfoResponse;

    public RegisterRestaurantResponse(AccountUserResponse accountUserResponse,
                                      RestaurantInfoResponse restaurantInfoResponse) {
        this.accountUserResponse = accountUserResponse;
        this.restaurantInfoResponse = restaurantInfoResponse;
    }

    public AccountUserResponse getAccountUserResponse() {
        return accountUserResponse;
    }

    public RestaurantInfoResponse getRestaurantInfoResponse() {
        return restaurantInfoResponse;
    }
}
