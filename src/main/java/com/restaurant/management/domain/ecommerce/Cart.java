package com.restaurant.management.domain.ecommerce;

import com.restaurant.management.domain.ecommerce.archive.CustomerArchive;
import com.restaurant.management.domain.ecommerce.archive.LineItemArchive;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart extends AbstractCart {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CustomerArchive customerArchive;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LineItemArchive> lineItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Company company;

    public Cart() {
    }

    public Cart(Boolean isOpen, Double totalPrice,
                CustomerArchive customerArchive, List<LineItemArchive> lineItems) {
        super(isOpen, totalPrice);
        this.customerArchive = customerArchive;
        this.lineItems = lineItems;
    }

    public Cart(Boolean isOpen, Double totalPrice,
                CustomerArchive customerArchive,
                List<LineItemArchive> lineItems, Company company) {
        super(isOpen, totalPrice);
        this.customerArchive = customerArchive;
        this.lineItems = lineItems;
        this.company = company;
    }

    public CustomerArchive getCustomerArchive() {
        return customerArchive;
    }

    public void setCustomerArchive(CustomerArchive customerArchive) {
        this.customerArchive = customerArchive;
    }

    public List<LineItemArchive> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItemArchive> lineItems) {
        this.lineItems = lineItems;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public static class CartBuilder {
        private Boolean isOpen;
        private Double totalPrice;
        private CustomerArchive customerArchive;
        private List<LineItemArchive> lineItems;
        private Company company;

        public CartBuilder setIsOpen(Boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }

        public CartBuilder setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public CartBuilder setCustomerArchive(CustomerArchive customerArchive) {
            this.customerArchive = customerArchive;
            return this;
        }

        public CartBuilder setLineItems(List<LineItemArchive> lineItems) {
            this.lineItems = lineItems;
            return this;
        }

        public CartBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public Cart build() {
            return new Cart(this.isOpen, this.totalPrice, this.customerArchive, this.lineItems, this.company);
        }
    }
}
