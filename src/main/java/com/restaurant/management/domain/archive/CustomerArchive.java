package com.restaurant.management.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customer_archive")
public class CustomerArchive extends AbstractUser{

    @Column(name = "phone_number")
    private Long phoneNumber;

    public CustomerArchive() {
    }

    public CustomerArchive(String name, String lastname, String email, Long phoneNumber) {
        super(name, lastname, email);
        this.phoneNumber = phoneNumber;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
