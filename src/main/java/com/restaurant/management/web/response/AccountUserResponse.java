package com.restaurant.management.web.response;

import com.restaurant.management.domain.dto.RoleDto;

import java.util.Set;

public class AccountUserResponse {
    private String name;
    private String lastname;
    private String email;
    private String username;
    private String userUniqueId;
    private Boolean isActive;
    private Set<RoleDto> roles;

    public AccountUserResponse(String name, String lastname, String email,
                               String username, String userUniqueId,
                               Boolean isActive, Set<RoleDto> roles) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.userUniqueId = userUniqueId;
        this.isActive = isActive;
        this.roles = roles;
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

    public String getUserUniqueId() {
        return userUniqueId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }
}
