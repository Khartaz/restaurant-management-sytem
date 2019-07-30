package com.restaurant.management.domain.ecommerce;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer extends AbstractUser {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Company company;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CustomerAddress customerAddress;

    public Customer() {
    }

    public Customer(String name, String lastname, String email, String phoneNumber,
                    Company company, CustomerAddress customerAddress) {
        super(name, lastname, email, phoneNumber);
        this.company = company;
        this.customerAddress = customerAddress;
    }

    public Customer(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                    Long id, String name, String lastname, String email, String phoneNumber,
                    CustomerAddress customerAddress) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, lastname, email, phoneNumber);
        this.customerAddress = customerAddress;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

    public static class CustomerBuilder {
        private String name;
        private String lastname;
        private String email;
        private String phoneNumber;
        private Company company;
        private CustomerAddress customerAddress;

        public CustomerBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CustomerBuilder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public CustomerBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CustomerBuilder setRestaurantInfo(Company company) {
            this.company = company;
            return this;
        }

        public CustomerBuilder setCustomerAddress(CustomerAddress customerAddress) {
            this.customerAddress = customerAddress;
            return this;
        }

        public Customer build() {
            return new Customer(this.name, this.lastname, this.email,
                    this.phoneNumber, this.company, this.customerAddress);
        }
    }
}
