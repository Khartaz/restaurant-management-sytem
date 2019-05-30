package com.restaurant.management.domain.dto;

public final class AddressDto {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String streetAndNumber;
    private String postCode;
    private String city;
    private String country;

    public AddressDto() {
    }

    public AddressDto(Long createdAt, Long updatedAt,
                      String createdByUserId, String updatedByUserId,
                      Long id, String streetAndNumber, String postCode,
                      String city, String country) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.streetAndNumber = streetAndNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
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

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
