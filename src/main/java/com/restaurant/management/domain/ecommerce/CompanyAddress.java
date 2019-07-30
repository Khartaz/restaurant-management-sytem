package com.restaurant.management.domain.ecommerce;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "company_address")
@Audited
public class CompanyAddress extends AbstractAddress {

    public CompanyAddress() {
    }

    public CompanyAddress(String streetAndNumber, String postCode,
                          String city, String country) {
        super(streetAndNumber, postCode, city, country);
    }

    public CompanyAddress(Long createdAt, Long updatedAt,
                          String createdByUserId, String updatedByUserId, Long id,
                          String streetAndNumber, String postCode, String city, String country) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, streetAndNumber, postCode, city, country);
    }

    public static class CompanyAddressBuilder {
        private String streetAndNumber;
        private String postCode;
        private String city;
        private String country;

        public CompanyAddressBuilder setStreetAndNumber(String streetAndNumber) {
            this.streetAndNumber = streetAndNumber;
            return this;
        }

        public CompanyAddressBuilder setPostCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public CompanyAddressBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public CompanyAddressBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public CompanyAddress build(){
            return new CompanyAddress(
                    this.streetAndNumber,
                    this.postCode,
                    this.city,
                    this.country
            );
        }
    }
}
