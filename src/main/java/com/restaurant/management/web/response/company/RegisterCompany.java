package com.restaurant.management.web.response.company;

import com.restaurant.management.domain.ecommerce.User;
import com.restaurant.management.domain.ecommerce.Company;

public final class RegisterCompany {
    private User user;
    private Company company;

    public RegisterCompany(User user, Company company) {
        this.user = user;
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public Company getRestaurantInfo() {
        return company;
    }
}
