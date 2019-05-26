package com.restaurant.management.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_address")
@Audited
public class RestaurantAddress extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street_and_number")
    private String streetAndNumber;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    public RestaurantAddress() {
    }

    public RestaurantAddress(String streetAndNumber, String postCode,
                             String city, String country) {
        this.streetAndNumber = streetAndNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }

    public RestaurantAddress(Long id, Long createdAt, Long updatedAt,
                             String createdByUserId, String updatedByUserId,
                             String streetAndNumber, String postCode, String city, String country) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId);
        this.id = id;
        this.streetAndNumber = streetAndNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
