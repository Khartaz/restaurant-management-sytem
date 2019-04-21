package com.restaurant.management.domain.dto;

public final class CustomerDto {
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

    public CustomerDto(Long id, String name, String lastname, Long phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
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
