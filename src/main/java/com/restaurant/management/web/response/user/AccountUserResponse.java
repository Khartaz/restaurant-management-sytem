package com.restaurant.management.web.response.user;

import com.restaurant.management.web.response.RoleResponse;

import java.util.Set;

public final class AccountUserResponse {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String username;
    private String emailVerificationToken;
    private Boolean isActive;
    private Set<RoleResponse> roles;

    public AccountUserResponse() {
    }

    public AccountUserResponse(Long id, String name, String lastname, String email,
                               String username, String emailVerificationToken,
                               Boolean isActive, Set<RoleResponse> roles) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.emailVerificationToken = emailVerificationToken;
        this.isActive = isActive;
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

    public String getUsername() {
        return username;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public Boolean isActive() {
        return isActive;
    }

    public Set<RoleResponse> getRoles() {
        return roles;
    }
}
