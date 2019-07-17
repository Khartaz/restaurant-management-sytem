package com.restaurant.management.domain.ecommerce;

import com.restaurant.management.domain.AbstractAddress;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customer_addresses")
public class CustomerAddress extends AbstractAddress {

    public CustomerAddress() {
    }

    public CustomerAddress(String streetAndNumber, String postCode,
                           String city, String country) {
        super(streetAndNumber, postCode, city, country);
    }

    public CustomerAddress(Long createdAt, Long updatedAt,
                           String createdByUserId, String updatedByUserId, Long id,
                           String streetAndNumber, String postCode, String city, String country) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, streetAndNumber, postCode, city, country);
    }

    public static class CustomerAddressBuilder {
        private String streetAndNumber;
        private String postCode;
        private String city;
        private String country;

        public CustomerAddressBuilder setStreetAndNumber(String streetAndNumber) {
            this.streetAndNumber = streetAndNumber;
            return this;
        }

        public CustomerAddressBuilder setPostCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public CustomerAddressBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public CustomerAddressBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public CustomerAddress build() {
            return new CustomerAddress(
                    this.streetAndNumber,
                    this.postCode,
                    this.city,
                    this.country
            );
        }
    }
}
