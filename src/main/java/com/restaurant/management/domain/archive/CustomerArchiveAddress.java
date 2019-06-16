package com.restaurant.management.domain.archive;

import com.restaurant.management.domain.AbstractAddress;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customer_archive_addresses")
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

    public static class CustomerArchiveAddressBuilder {
        private String streetAndNumber;
        private String postCode;
        private String city;
        private String country;

        public CustomerArchiveAddressBuilder setStreetAndNumber(String streetAndNumber) {
            this.streetAndNumber = streetAndNumber;
            return this;
        }

        public CustomerArchiveAddressBuilder setPostCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public CustomerArchiveAddressBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public CustomerArchiveAddressBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public CustomerArchiveAddress build() {
            return new CustomerArchiveAddress(this.streetAndNumber, this.postCode, this.city, this.country);
        }
    }
}
