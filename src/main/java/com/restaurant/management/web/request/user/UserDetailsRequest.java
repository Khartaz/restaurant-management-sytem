package com.restaurant.management.web.request.user;

public final class UserDetailsRequest {
    private String name;
    private String lastName;
    private String phone;

    public UserDetailsRequest() {

    }

    public UserDetailsRequest(String name, String lastName, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
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

}
