package com.restaurant.management.web.response.company;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.Company;

public final class RegisterCompany {

    private AccountUser accountUser;

    private Company company;

    public RegisterCompany(AccountUser accountUser, Company company) {
        this.accountUser = accountUser;
        this.company = company;
    }

    public AccountUser getAccountUser() {
        return accountUser;
    }

    public Company getRestaurantInfo() {
        return company;
    }
}
