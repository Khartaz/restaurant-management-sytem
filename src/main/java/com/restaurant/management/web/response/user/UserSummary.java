package com.restaurant.management.web.response.user;

import com.restaurant.management.domain.Role;

import java.util.Set;

public final class UserSummary {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private Long phoneNumber;
    private Set<Role> roles;

    public UserSummary() {
    }

    public UserSummary(Long id, String name,
                       String lastname, String email, Long phoneNumber,
                      Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
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

    public Set<Role> getRoles() {
        return roles;
    }
}
