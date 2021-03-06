package com.restaurant.management.domain.ecommerce;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "account_user_addresses")
public class UserAddress extends AbstractAddress {

    public UserAddress() {
    }

    public UserAddress(String streetAndNumber, String postCode,
                       String city, String country) {
        super(streetAndNumber, postCode, city, country);
    }

    public UserAddress(Long createdAt, Long updatedAt,
                       String createdByUserId, String updatedByUserId, Long id,
                       String streetAndNumber, String postCode, String city, String country) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, streetAndNumber, postCode, city, country);
    }

    public static class AccountUserAddressBuilder {
        private String streetAndNumber;
        private String postCode;
        private String city;
        private String country;

        public AccountUserAddressBuilder setStreetAndNumber(String streetAndNumber) {
            this.streetAndNumber = streetAndNumber;
            return this;
        }

        public AccountUserAddressBuilder setPostCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public AccountUserAddressBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public AccountUserAddressBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public UserAddress build() {
            return new UserAddress(
                    this.streetAndNumber,
                    this.postCode,
                    this.city,
                    this.country
            );
        }
    }

}
