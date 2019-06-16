package com.restaurant.management.web.response.user;

import com.restaurant.management.domain.Role;
import com.restaurant.management.web.response.restaurant.RestaurantInfoResponse;

import java.util.Set;

public final class UserSummary {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Set<Role> roles;

    private RestaurantInfoResponse restaurantInfoResponse;

    public UserSummary() {
    }

    public UserSummary(Long id, String name,
                       String lastname, String email, String phoneNumber,
                       Set<Role> roles, RestaurantInfoResponse restaurantInfoResponse) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.restaurantInfoResponse = restaurantInfoResponse;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public RestaurantInfoResponse getRestaurantInfoResponse() {
        return restaurantInfoResponse;
    }
}
