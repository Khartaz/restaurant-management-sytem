package com.restaurant.management.domain.ecommerce.dto;

public class CustomerFormDTO {
    private String createdAt;
    private String updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String name;
    private String lastName;
    private String phone;
    private String email;
    private String streetAndNumber;
    private String postCode;
    private String city;
    private String country;

    public CustomerFormDTO() {
    }

    public CustomerFormDTO(String createdAt, String updatedAt, String createdByUserId,
                           String updatedByUserId, Long id, String name,
                           String lastName, String phone, String email,
                           String streetAndNumber, String postCode, String city, String country) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.streetAndNumber = streetAndNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
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

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
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
