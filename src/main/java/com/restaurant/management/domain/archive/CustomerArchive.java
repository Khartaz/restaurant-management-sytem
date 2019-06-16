package com.restaurant.management.domain.archive;

import com.restaurant.management.domain.AbstractUser;
import com.restaurant.management.domain.RestaurantInfo;

import javax.persistence.*;

@Entity
@Table(name = "customer_archive")
public class CustomerArchive extends AbstractUser {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private RestaurantInfo restaurantInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CustomerArchiveAddress customerArchiveAddress;

    public CustomerArchive() {
    }

    public CustomerArchive(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                           Long id, String name, String lastname, String email, String phoneNumber,
                           CustomerArchiveAddress customerArchiveAddress) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, lastname, email, phoneNumber);
        this.customerArchiveAddress = customerArchiveAddress;
    }

    public CustomerArchive(String name, String lastname, String email, String phoneNumber,
                           RestaurantInfo restaurantInfo, CustomerArchiveAddress customerArchiveAddress) {
        super(name, lastname, email, phoneNumber);
        this.restaurantInfo = restaurantInfo;
        this.customerArchiveAddress = customerArchiveAddress;
    }

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }

    public CustomerArchiveAddress getCustomerArchiveAddress() {
        return customerArchiveAddress;
    }

    public void setCustomerArchiveAddress(CustomerArchiveAddress customerArchiveAddress) {
        this.customerArchiveAddress = customerArchiveAddress;
    }

    public static class CustomerArchiveBuilder {
        private String name;
        private String lastname;
        private String email;
        private String phoneNumber;
        private RestaurantInfo restaurantInfo;
        private CustomerArchiveAddress customerArchiveAddress;

        public CustomerArchiveBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CustomerArchiveBuilder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public CustomerArchiveBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerArchiveBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CustomerArchiveBuilder setRestaurantInfo(RestaurantInfo restaurantInfo) {
            this.restaurantInfo = restaurantInfo;
            return this;
        }

        public CustomerArchiveBuilder setCustomerArchiveAddress(CustomerArchiveAddress customerArchiveAddress) {
            this.customerArchiveAddress = customerArchiveAddress;
            return this;
        }

        public CustomerArchive build() {
            return new CustomerArchive(this.name, this.lastname, this.email, this.phoneNumber, this.restaurantInfo, this.customerArchiveAddress);
        }
    }
}
