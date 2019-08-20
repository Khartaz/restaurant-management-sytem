package com.restaurant.management.domain.ecommerce.dto;

public final class PersonnelDTO {
    private String createdAt;
    private String updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String jobTitle;
    private String role;
    private Boolean isActive;
    private AddressDTO address;

    public PersonnelDTO() {
    }

    public PersonnelDTO(String createdAt, String updatedAt, String createdByUserId,
                        String updatedByUserId, Long id, String name, String lastName,
                        String email, String phone, String jobTitle,
                        String role, Boolean isActive, AddressDTO address) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
        this.role = role;
        this.isActive = isActive;
        this.address = address;
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

    public AddressDTO getAddress() {
        return address;
    }
}
