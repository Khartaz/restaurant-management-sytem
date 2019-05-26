package com.restaurant.management.domain;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer extends AbstractUser  {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    public Customer() {
    }

    public Customer(String name, String lastname, String email, Long phoneNumber,
                    RestaurantInfo restaurantInfo) {
        super(name, lastname, email, phoneNumber);
        this.restaurantInfo = restaurantInfo;
    }

    public Customer(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                    Long id, String name, String lastname, String email, Long phoneNumber) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, lastname, email, phoneNumber);
    }

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }

    public static class CustomerBuilder {
        private String name;
        private String lastname;
        private String email;
        private Long phoneNumber;
        private RestaurantInfo restaurantInfo;

        public CustomerBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CustomerBuilder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public CustomerBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerBuilder setPhoneNumber(Long phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CustomerBuilder setRestaurantInfo(RestaurantInfo restaurantInfo) {
            this.restaurantInfo = restaurantInfo;
            return this;
        }

        public Customer build() {
            return new Customer(this.name, this.lastname, this.email, this.phoneNumber, this.restaurantInfo);
        }
    }
}
