package com.restaurant.management.domain.archive;

import com.restaurant.management.domain.AbstractAddress;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customer_archive_address")
public class CustomerArchiveAddress extends AbstractAddress {

    public CustomerArchiveAddress() {
    }

    public CustomerArchiveAddress(String streetAndNumber, String postCode,
                           String city, String country) {
        super(streetAndNumber, postCode, city, country);
    }

    public CustomerArchiveAddress(Long createdAt, Long updatedAt,
                           String createdByUserId, String updatedByUserId, Long id,
                           String streetAndNumber, String postCode, String city, String country) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, streetAndNumber, postCode, city, country);
    }

}
