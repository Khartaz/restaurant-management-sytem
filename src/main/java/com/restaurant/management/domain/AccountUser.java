package com.restaurant.management.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "account_users")
public class AccountUser extends AbstractAdmin {

    public AccountUser() {
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public String getUserUniqueId() {
        return super.getUserUniqueId();
    }

    @Override
    public void setUserUniqueId(String userUniqueId) {
        super.setUserUniqueId(userUniqueId);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getLastname() {
        return super.getLastname();
    }

    @Override
    public void setLastname(String lastname) {
        super.setLastname(lastname);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }


}
