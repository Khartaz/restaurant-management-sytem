package com.restaurant.management.domain.archive;

import com.restaurant.management.domain.AbstractUser;
import com.restaurant.management.domain.RestaurantInfo;

import javax.persistence.*;

@Entity
@Table(name = "customer_archive")
public class CustomerArchive extends AbstractUser {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CustomerArchiveAddress customerArchiveAddress;

    public CustomerArchive() {
    }

    public CustomerArchive(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                           Long id, String name, String lastname, String email, Long phoneNumber,
                           RestaurantInfo restaurantInfo, CustomerArchiveAddress customerArchiveAddress) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, lastname, email, phoneNumber);
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
}
