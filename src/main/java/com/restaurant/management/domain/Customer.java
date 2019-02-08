package com.restaurant.management.domain;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer extends AbstractUser {

    public Customer() {
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

}
