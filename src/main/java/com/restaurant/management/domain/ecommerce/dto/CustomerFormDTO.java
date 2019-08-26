package com.restaurant.management.domain.ecommerce.dto;

public final class CustomerFormDTO {
    private String createdAt;
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

    public CustomerFormDTO(String createdAt, Long id,
                           String name, String lastName, String phone,
                           String email, String streetAndNumber,
                           String postCode, String city, String country) {
        this.createdAt = createdAt;
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
