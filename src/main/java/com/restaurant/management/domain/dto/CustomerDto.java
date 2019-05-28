package com.restaurant.management.domain.dto;

import com.restaurant.management.domain.RestaurantInfo;

public final class CustomerDto {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String name;
    private String lastname;
    private Long phoneNumber;
    private String email;
    private RestaurantInfo restaurantInfo;

    public CustomerDto() {
    }

    public CustomerDto(String name, String lastname, Long phoneNumber, String email) {
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public CustomerDto(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                       Long id, String name, String lastname, Long phoneNumber, String email,
                       RestaurantInfo restaurantInfo) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.restaurantInfo = restaurantInfo;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public String getUpdatedByUserId() {
        return updatedByUserId;
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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }
}
