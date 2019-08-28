package com.restaurant.management.domain.ecommerce.dto;

public final class CompanyFormDTO {
    private Long createdAt;
    private Long id;
    private String name;
    private String phone;
    private String streetAndNumber;
    private String postCode;
    private String city;
    private String country;

    public CompanyFormDTO() {
    }

    public CompanyFormDTO(Long createdAt, Long id, String name, String phone,
                          String streetAndNumber, String postCode, String city,
                          String country) {
        this.createdAt = createdAt;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.streetAndNumber = streetAndNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
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
