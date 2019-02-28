package com.restaurant.management.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "daily_orders_list")
public class DailyOrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Column(name = "daily_income")
    private Double dailyIncome;

    @Column(name = "is_opened")
    private Boolean isOpened;

    public DailyOrderList() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Double getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(Double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public Boolean getOpened() {
        return isOpened;
    }

    public void setOpened(Boolean opened) {
        isOpened = opened;
    }

    //To fix/check
    public Double calculateDailyIncome() {
        return orders.stream()
                .mapToDouble(v -> v.getTotalPrice() * orders.size())
                .reduce(Double::sum)
                .getAsDouble();
    }
}
