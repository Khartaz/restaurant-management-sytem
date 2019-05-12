package com.restaurant.management.web.response.user;

import com.restaurant.management.web.response.RoleResponse;

import java.util.Set;

public final class AccountUserResponse {

    private Long createdAt;
    private Long updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private Long phoneNumber;
    private String username;
    private String emailVerificationToken;
    private Boolean isActive;
    private Set<RoleResponse> roles;

    public AccountUserResponse() {
    }

    public AccountUserResponse(Long createdAt, Long updatedAt, String createdBy,
                               String updatedBy, Long id, String name,
                               String lastname, String email, Long phoneNumber, String username,
                               String emailVerificationToken, Boolean isActive,
                               Set<RoleResponse> roles) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.emailVerificationToken = emailVerificationToken;
        this.isActive = isActive;
        this.roles = roles;
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

    public String getEmail() {
        return email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
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
