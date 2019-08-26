package com.restaurant.management.domain.ecommerce.dto;

public final class PersonnelFormDTO {
    private String createdAt;
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String jobTitle;
    private String role;
    private Boolean isActive;
    private String streetAndNumber;
    private String postCode;
    private String city;
    private String country;

    public PersonnelFormDTO() {
    }

    public PersonnelFormDTO(String createdAt,
                            Long id,
                            String name,
                            String lastName,
                            String email,
                            String phone,
                            String jobTitle,
                            String role,
                            Boolean isActive,
                            String streetAndNumber,
                            String postCode,
                            String city,
                            String country) {
        this.createdAt = createdAt;
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
        this.role = role;
        this.isActive = isActive;
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

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getRole() {
        return role;
    }

    public Boolean getActive() {
        return isActive;
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
