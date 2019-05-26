package com.restaurant.management.web.response.restaurant;

public final class RestaurantInfoResponse {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String name;

    private RestaurantAddressResponse restaurantAddressResponse;

    public RestaurantInfoResponse() {
    }

    public RestaurantInfoResponse(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                                  Long id, String name, RestaurantAddressResponse restaurantAddressResponse) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.restaurantAddressResponse = restaurantAddressResponse;
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

    public RestaurantAddressResponse getRestaurantAddressResponse() {
        return restaurantAddressResponse;
    }
}
