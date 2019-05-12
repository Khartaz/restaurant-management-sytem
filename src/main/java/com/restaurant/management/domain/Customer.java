package com.restaurant.management.domain;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer extends AbstractUser  {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    public Customer() {
    }

    public Customer(String name, String lastname, String email, Long phoneNumber) {
        super(name, lastname, email, phoneNumber);
    }

    public Customer(Long createdAt, Long updatedAt, String createdBy, String updatedBy,
                    Long id, String name, String lastname, String email, Long phoneNumber) {
        super(createdAt, updatedAt, createdBy, updatedBy, id, name, lastname, email, phoneNumber);
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

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }
}
