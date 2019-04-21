package com.restaurant.management.web.response;

public final class CustomerResponse {

    private Long id;
    private String name;
    private String lastname;
    private String email;
    private Long phoneNumber;

    public CustomerResponse() {
    }

    public CustomerResponse(Long id, String name, String lastname, String email, Long phoneNumber) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public CustomerResponse(String name, String lastname, String email, Long phoneNumber) {
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

    public String getEmail() {
        return email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }
}
