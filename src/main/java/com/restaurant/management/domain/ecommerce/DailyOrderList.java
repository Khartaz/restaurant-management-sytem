package com.restaurant.management.domain.ecommerce;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "daily_orders_list")
public class DailyOrderList extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "daily_income")
    private Double dailyIncome;

    @Column(name = "number_of_orders")
    private Integer numberOfOrders;

    @Column(name = "is_open")
    private Boolean isOpen;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Order> orders = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Company company;

    public DailyOrderList(Long id, Double dailyIncome,
                          Integer numberOfOrders,
                          Boolean isOpen, Set<Order> orders) {
        this.id = id;
        this.dailyIncome = dailyIncome;
        this.numberOfOrders = numberOfOrders;
        this.isOpen = isOpen;
        this.orders = orders;
    }

    public DailyOrderList(Double dailyIncome, Integer numberOfOrders,
                          Boolean isOpen, Set<Order> orders, Company company) {
        this.dailyIncome = dailyIncome;
        this.numberOfOrders = numberOfOrders;
        this.isOpen = isOpen;
        this.orders = orders;
        this.company = company;
    }

    public DailyOrderList() {
    }

    public Long getId() {
        return id;
    }

    public Double getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(Double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public Boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public static class DailyOrderListBuilder {
        private Double dailyIncome;
        private Integer numberOfOrders;
        private Boolean isOpen;
        private Set<Order> orders;
        private Company company;

        public DailyOrderListBuilder setDailyIncome(Double dailyIncome) {
            this.dailyIncome = dailyIncome;
            return this;
        }

        public DailyOrderListBuilder setNumberOfOrders(Integer numberOfOrders) {
            this.numberOfOrders = numberOfOrders;
            return this;
        }

        public DailyOrderListBuilder setIsOpen(Boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }

        public DailyOrderListBuilder setOrders(Set<Order> orders) {
            this.orders = orders;
            return this;
        }

        public DailyOrderListBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public DailyOrderList build() {
            return new DailyOrderList(this.dailyIncome, this.numberOfOrders, this.isOpen, this.orders, this.company);
        }
    }
}
