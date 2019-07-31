package com.restaurant.management.domain.ecommerce;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts_ordered")
public class CartOrdered extends AbstractCart {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CustomerOrdered customerOrdered;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LineItemOrdered> lineItemsOrdered = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Company company;

    public CartOrdered() {
    }

    public CartOrdered(Boolean isOpen, Double totalPrice,
                       CustomerOrdered customerOrdered, List<LineItemOrdered> lineItemsOrdered) {
        super(isOpen, totalPrice);
        this.customerOrdered = customerOrdered;
        this.lineItemsOrdered = lineItemsOrdered;
    }

    public CartOrdered(Boolean isOpen, Double totalPrice,
                       CustomerOrdered customerOrdered,
                       List<LineItemOrdered> lineItemsOrdered, Company company) {
        super(isOpen, totalPrice);
        this.customerOrdered = customerOrdered;
        this.lineItemsOrdered = lineItemsOrdered;
        this.company = company;
    }

    public CustomerOrdered getCustomerOrdered() {
        return customerOrdered;
    }

    public void setCustomerOrdered(CustomerOrdered customerOrdered) {
        this.customerOrdered = customerOrdered;
    }

    public List<LineItemOrdered> getLineItemsOrdered() {
        return lineItemsOrdered;
    }

    public void setLineItemsOrdered(List<LineItemOrdered> lineItemsOrdered) {
        this.lineItemsOrdered = lineItemsOrdered;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public static class CartOrderedBuilder {
        private Boolean isOpen;
        private Double totalPrice;
        private CustomerOrdered customerOrdered;
        private List<LineItemOrdered> lineItems;
        private Company company;

        public CartOrderedBuilder setIsOpen(Boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }

        public CartOrderedBuilder setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public CartOrderedBuilder setCustomerOrdered(CustomerOrdered customerOrdered) {
            this.customerOrdered = customerOrdered;
            return this;
        }

        public CartOrderedBuilder setLineItems(List<LineItemOrdered> lineItems) {
            this.lineItems = lineItems;
            return this;
        }

        public CartOrderedBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public CartOrdered build() {
            return new CartOrdered(this.isOpen, this.totalPrice, this.customerOrdered, this.lineItems, this.company);
        }
    }
}
