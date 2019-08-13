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

    public Customer(String name, String lastName, String email, String phone, String jobTitle,
                    Company company, CustomerAddress customerAddress) {
        super(name, lastName, email, phone, jobTitle);
        this.company = company;
        this.customerAddress = customerAddress;
    }

    public Customer(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                    Long id, String name, String lastName, String email, String phone, String jobTitle,
                    CustomerAddress customerAddress) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, lastName, email, phone, jobTitle);
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

}
