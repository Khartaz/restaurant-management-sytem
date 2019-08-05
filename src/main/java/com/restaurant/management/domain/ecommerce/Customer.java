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

    public Customer(String name, String lastName, String email, String phone,
                    Company company, CustomerAddress customerAddress) {
        super(name, lastName, email, phone);
        this.company = company;
        this.customerAddress = customerAddress;
    }

    public Customer(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                    Long id, String name, String lastName, String email, String phone,
                    CustomerAddress customerAddress) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, lastName, email, phone);
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
        private String lastName;
        private String email;
        private String phone;
        private Company company;
        private CustomerAddress customerAddress;

        public CustomerBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CustomerBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CustomerBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerBuilder setPhone(String phone) {
            this.phone = phone;
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
            return new Customer(this.name, this.lastName, this.email,
                    this.phone, this.company, this.customerAddress);
        }
    }
}
