package com.restaurant.management.domain;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer extends AbstractUser  {

    @Column(name = "phone_number")
    private Long phoneNumber;

    public Customer() {
    }

    public Customer(String name, String lastname, String email, Long phoneNumber) {
        super(name, lastname, email);
        this.phoneNumber = phoneNumber;
    }

    public Customer(Long id, String name, String lastname, String email, Long phoneNumber) {
        super(id, name, lastname, email);
        this.phoneNumber = phoneNumber;
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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
