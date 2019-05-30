package com.restaurant.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@MappedSuperclass
@JsonIgnoreProperties(allowGetters = true)
public class AbstractAddress extends AbstractAuditing {

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

    public AbstractAddress() {
    }

    public AbstractAddress(String streetAndNumber, String postCode, String city, String country) {
        this.streetAndNumber = streetAndNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }

    public AbstractAddress(Long createdAt, Long updatedAt, String createdByUserId,
                           String updatedByUserId, Long id, String streetAndNumber,
                           String postCode, String city, String country) {
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

}
