package com.restaurant.management.domain.ecommerce;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customer_ordered_addresses")
public class CustomerOrderedAddress extends AbstractAddress {

    public CustomerOrderedAddress() {
    }

    public CustomerOrderedAddress(String streetAndNumber, String postCode,
                                  String city, String country) {
        super(streetAndNumber, postCode, city, country);
    }

    public CustomerOrderedAddress(Long createdAt, Long updatedAt,
                                  String createdByUserId, String updatedByUserId, Long id,
                                  String streetAndNumber, String postCode, String city, String country) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, streetAndNumber, postCode, city, country);
    }

    public static class CustomerOrderedAddressBuilder {
        private String streetAndNumber;
        private String postCode;
        private String city;
        private String country;

        public CustomerOrderedAddressBuilder setStreetAndNumber(String streetAndNumber) {
            this.streetAndNumber = streetAndNumber;
            return this;
        }

        public CustomerOrderedAddressBuilder setPostCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public CustomerOrderedAddressBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public CustomerOrderedAddressBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public CustomerOrderedAddress build() {
            return new CustomerOrderedAddress(this.streetAndNumber, this.postCode, this.city, this.country);
        }
    }
}
