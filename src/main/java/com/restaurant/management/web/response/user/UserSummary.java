package com.restaurant.management.web.response.user;

import com.restaurant.management.domain.ecommerce.Role;
import com.restaurant.management.web.response.company.CompanyResponse;

import java.util.Set;

public final class UserSummary {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private Set<Role> roles;

    private CompanyResponse companyResponse;

    public UserSummary() {
    }

    public UserSummary(Long id, String name,
                       String lastName, String email, String phone,
                       Set<Role> roles, CompanyResponse companyResponse) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
        this.companyResponse = companyResponse;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public CompanyResponse getCompanyResponse() {
        return companyResponse;
    }
}
