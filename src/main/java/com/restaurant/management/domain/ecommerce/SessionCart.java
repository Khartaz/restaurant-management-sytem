package com.restaurant.management.domain.ecommerce;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "session_carts")
public class SessionCart extends AbstractCart {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SessionLineItem> sessionLineItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Company company;

    public SessionCart() {
    }

    public SessionCart(Long id, Boolean isOpen, Double totalPrice,
                       Customer customer, List<SessionLineItem> sessionLineItems) {
        super(id, isOpen, totalPrice);
        this.customer = customer;
        this.sessionLineItems = sessionLineItems;
    }

    public SessionCart(Boolean isOpen, Double totalPrice,
                       Customer customer, List<SessionLineItem> sessionLineItems,
                       Company company) {
        super(isOpen, totalPrice);
        this.customer = customer;
        this.sessionLineItems = sessionLineItems;
        this.company = company;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<SessionLineItem> getSessionLineItems() {
        return sessionLineItems;
    }

    public void setSessionLineItems(List<SessionLineItem> sessionLineItems) {
        this.sessionLineItems = sessionLineItems;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Double calculateTotalPriceOf(SessionCart sessionCart) {
        double price = Stream.of(sessionCart.getSessionLineItems())
                .flatMapToDouble(v -> v.stream()
                        .mapToDouble(AbstractLineItem::getPrice))
                .sum();

        return Math.floor(price * 100) / 100;
    }

    public static class SessionCartBuilder {
        private Boolean isOpen;
        private Double totalPrice;
        private Customer customer;
        private List<SessionLineItem> sessionLineItems;
        private Company company;

        public SessionCartBuilder setIsOpen(Boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }

        public SessionCartBuilder setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public SessionCartBuilder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public SessionCartBuilder setSessionLineItems(List<SessionLineItem> sessionLineItems) {
            this.sessionLineItems = sessionLineItems;
            return this;
        }

        public SessionCartBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public SessionCart build() {
            return new SessionCart(this.isOpen, this.totalPrice, this.customer,
                    this.sessionLineItems, this.company);
        }
    }
}
