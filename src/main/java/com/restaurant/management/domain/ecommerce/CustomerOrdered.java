package com.restaurant.management.domain.ecommerce;

import javax.persistence.*;

@Entity
@Table(name = "customer_ordered")
public class CustomerOrdered extends AbstractUser {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Company company;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CustomerOrderedAddress customerOrderedAddress;

    public CustomerOrdered() {
    }

    public CustomerOrdered(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                           Long id, String name, String lastname, String email, String phoneNumber,
                           CustomerOrderedAddress customerOrderedAddress) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, lastname, email, phoneNumber);
        this.customerOrderedAddress = customerOrderedAddress;
    }

    public CustomerOrdered(String name, String lastname, String email, String phoneNumber,
                           Company company, CustomerOrderedAddress customerOrderedAddress) {
        super(name, lastname, email, phoneNumber);
        this.company = company;
        this.customerOrderedAddress = customerOrderedAddress;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public CustomerOrderedAddress getCustomerOrderedAddress() {
        return customerOrderedAddress;
    }

    public void setCustomerOrderedAddress(CustomerOrderedAddress customerOrderedAddress) {
        this.customerOrderedAddress = customerOrderedAddress;
    }

    public static class CustomerOrderedBuilder {
        private String name;
        private String lastname;
        private String email;
        private String phoneNumber;
        private Company company;
        private CustomerOrderedAddress customerOrderedAddress;

        public CustomerOrderedBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CustomerOrderedBuilder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public CustomerOrderedBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerOrderedBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CustomerOrderedBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public CustomerOrderedBuilder setCustomerOrderedAddress(CustomerOrderedAddress customerOrderedAddress) {
            this.customerOrderedAddress = customerOrderedAddress;
            return this;
        }

        public CustomerOrdered build() {
            return new CustomerOrdered(this.name, this.lastname, this.email, this.phoneNumber, this.company, this.customerOrderedAddress);
        }
    }
}
