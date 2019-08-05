package com.restaurant.management.domain.ecommerce;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "carts")
public class Cart extends AbstractCart {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LineItem> lineItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Company company;

    public Cart() {
    }

    public Cart(Long id, Boolean isOpen, Double totalPrice,
                Customer customer, List<LineItem> lineItems) {
        super(id, isOpen, totalPrice);
        this.customer = customer;
        this.lineItems = lineItems;
    }

    public Cart(Boolean isOpen, Double totalPrice,
                Customer customer, List<LineItem> lineItems,
                Company company) {
        super(isOpen, totalPrice);
        this.customer = customer;
        this.lineItems = lineItems;
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Double calculateTotalPriceOf(Cart cart) {
        double price = Stream.of(cart.getLineItems())
                .flatMapToDouble(v -> v.stream()
                        .mapToDouble(AbstractLineItem::getPrice))
                .sum();

        return Math.floor(price * 100) / 100;
    }

    public static class CartBuilder {
        private Boolean isOpen;
        private Double totalPrice;
        private Customer customer;
        private List<LineItem> lineItems;
        private Company company;

        public CartBuilder setIsOpen(Boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }

        public CartBuilder setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public CartBuilder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public CartBuilder setLineItems(List<LineItem> lineItems) {
            this.lineItems = lineItems;
            return this;
        }

        public CartBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public Cart build() {
            return new Cart(this.isOpen, this.totalPrice, this.customer,
                    this.lineItems, this.company);
        }
    }
}
