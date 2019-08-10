package com.restaurant.management.domain.ecommerce.dto;

public class PersonnelFormDTO {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private String streetAndNumber;
    private String postCode;
    private String city;
    private String country;
    private Boolean isActive;

    public PersonnelFormDTO() {
    }

    public PersonnelFormDTO(Long createdAt, Long updatedAt, String createdByUserId,
                            String updatedByUserId, Long id, String name, String lastName,
                            String email, String phone, String role,
                            String streetAndNumber, String postCode,
                            String city, String country, Boolean isActive) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.streetAndNumber = streetAndNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
        this.isActive = isActive;
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

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
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

    public Boolean getActive() {
        return isActive;
    }
}
