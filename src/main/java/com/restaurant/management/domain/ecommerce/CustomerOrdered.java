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
                           Long id, String name, String lastName, String email, String phone,
                           CustomerOrderedAddress customerOrderedAddress) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, lastName, email, phone);
        this.customerOrderedAddress = customerOrderedAddress;
    }

    public CustomerOrdered(String name, String lastName, String email, String phone, String jobTitle,
                           Company company, CustomerOrderedAddress customerOrderedAddress) {
        super(name, lastName, email, phone, jobTitle);
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
}
