package com.restaurant.management.domain.dto;

public class CustomerDto {
    private Long id;
    private String name;
    private String lastname;
    private Long phoneNumber;
    private String email;

    public CustomerDto(Long id, String name, String lastname, String email, Long phoneNumber) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
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
