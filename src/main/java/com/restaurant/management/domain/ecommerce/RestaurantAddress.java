package com.restaurant.management.domain.ecommerce;

import com.restaurant.management.domain.AbstractAddress;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_addresses")
@Audited
public class RestaurantAddress extends AbstractAddress {

    public RestaurantAddress() {
    }

    public RestaurantAddress(String streetAndNumber, String postCode,
                             String city, String country) {
        super(streetAndNumber, postCode, city, country);
    }

    public RestaurantAddress(Long createdAt, Long updatedAt,
                             String createdByUserId, String updatedByUserId, Long id,
                             String streetAndNumber, String postCode, String city, String country) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, streetAndNumber, postCode, city, country);
    }

    public static class RestaurantAddressBuilder {
        private String streetAndNumber;
        private String postCode;
        private String city;
        private String country;

        public RestaurantAddressBuilder setStreetAndNumber(String streetAndNumber) {
            this.streetAndNumber = streetAndNumber;
            return this;
        }

        public RestaurantAddressBuilder setPostCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public RestaurantAddressBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public RestaurantAddressBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public RestaurantAddress build(){
            return new RestaurantAddress(
                    this.streetAndNumber,
                    this.postCode,
                    this.city,
                    this.country
            );
        }
    }
}
