package com.restaurant.management.domain.dto;

public final class CustomerDto {
    private Long createdAt;
    private Long updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long id;
    private String name;
    private String lastname;
    private Long phoneNumber;
    private String email;

    public CustomerDto() {
    }

    public CustomerDto(String name, String lastname, Long phoneNumber, String email) {
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public CustomerDto(Long createdAt, Long updatedAt, String createdBy, String updatedBy,
                       Long id, String name, String lastname, Long phoneNumber, String email) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
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
}
