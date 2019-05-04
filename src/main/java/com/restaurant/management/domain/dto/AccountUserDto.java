package com.restaurant.management.domain.dto;

import java.util.Set;

public final class AccountUserDto {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String emailVerificationToken;
    private Boolean isActive;
    private Set<RoleDto> roles;

    public AccountUserDto(Long id, String name, String lastname,
                          String email, String username,
                          String emailVerificationToken,
                          Boolean isActive, Set<RoleDto> roles) {
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

    public String getPassword() {
        return password;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public Boolean isActive() {
        return isActive;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }
}
