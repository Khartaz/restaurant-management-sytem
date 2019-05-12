package com.restaurant.management.web.response;

import java.util.Set;

public final class DailyOrderListResponse {
    private Long id;
    private Double dailyIncome;
    private Integer numberOfOrders;
    private Boolean isOpen;
    private Set<OrderResponse> orderResponse;

    public DailyOrderListResponse() {
    }

    public DailyOrderListResponse(Long id, Double dailyIncome,
                                  Integer numberOfOrders,
                                  Boolean isOpen, Set<OrderResponse> orderResponse) {
        this.id = id;
        this.dailyIncome = dailyIncome;
        this.numberOfOrders = numberOfOrders;
        this.isOpen = isOpen;
        this.orderResponse = orderResponse;
    }

    public Long getId() {
        return id;
    }

    public Double getDailyIncome() {
        return dailyIncome;
    }

    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    public Boolean isOpen() {
        return isOpen;
    }

    public Set<OrderResponse> getOrderResponse() {
        return orderResponse;
    }
}
