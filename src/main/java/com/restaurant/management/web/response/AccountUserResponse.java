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

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserUniqueId() {
        return userUniqueId;
    }

    public void setUserUniqueId(String userUniqueId) {
        this.userUniqueId = userUniqueId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }
}
