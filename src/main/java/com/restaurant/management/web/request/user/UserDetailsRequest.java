package com.restaurant.management.web.request.user;

public final class UserDetailsRequest {
    private String name;
    private String lastname;
    private String phoneNumber;

    public UserDetailsRequest() {

    }

    public UserDetailsRequest(String name, String lastname, String phoneNumber) {
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

}
