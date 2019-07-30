package com.restaurant.management.domain.ecommerce.archive;

import com.restaurant.management.domain.ecommerce.AbstractUser;
import com.restaurant.management.domain.ecommerce.Company;

import javax.persistence.*;

@Entity
@Table(name = "customer_archive")
public class CustomerArchive extends AbstractUser {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Company company;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CustomerArchiveAddress customerArchiveAddress;

    public CustomerArchive() {
    }

    public CustomerArchive(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                           Long id, String name, String lastname, String email, String phoneNumber,
                           CustomerArchiveAddress customerArchiveAddress) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, lastname, email, phoneNumber);
        this.customerArchiveAddress = customerArchiveAddress;
    }

    public CustomerArchive(String name, String lastname, String email, String phoneNumber,
                           Company company, CustomerArchiveAddress customerArchiveAddress) {
        super(name, lastname, email, phoneNumber);
        this.company = company;
        this.customerArchiveAddress = customerArchiveAddress;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public CustomerArchiveAddress getCustomerArchiveAddress() {
        return customerArchiveAddress;
    }

    public void setCustomerArchiveAddress(CustomerArchiveAddress customerArchiveAddress) {
        this.customerArchiveAddress = customerArchiveAddress;
    }

    public static class CustomerArchiveBuilder {
        private String name;
        private String lastname;
        private String email;
        private String phoneNumber;
        private Company company;
        private CustomerArchiveAddress customerArchiveAddress;

        public CustomerArchiveBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CustomerArchiveBuilder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public CustomerArchiveBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerArchiveBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CustomerArchiveBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public CustomerArchiveBuilder setCustomerArchiveAddress(CustomerArchiveAddress customerArchiveAddress) {
            this.customerArchiveAddress = customerArchiveAddress;
            return this;
        }

        public CustomerArchive build() {
            return new CustomerArchive(this.name, this.lastname, this.email, this.phoneNumber, this.company, this.customerArchiveAddress);
        }
    }
}
