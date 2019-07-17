package com.restaurant.management.web.response.restaurant;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.ecommerce.RestaurantInfo;

public final class RegisterRestaurant {

    private AccountUser accountUser;

    private RestaurantInfo restaurantInfo;

    public RegisterRestaurant(AccountUser accountUser, RestaurantInfo restaurantInfo) {
        this.accountUser = accountUser;
        this.restaurantInfo = restaurantInfo;
    }

    public AccountUser getAccountUser() {
        return accountUser;
    }

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }
}
