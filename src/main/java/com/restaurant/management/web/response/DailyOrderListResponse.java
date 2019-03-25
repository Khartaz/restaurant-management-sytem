package com.restaurant.management.web.response;

import java.util.Set;

public class DailyOrderListResponse {
    private Long id;
    private String uniqueId;
    private Double dailyIncome;
    private Boolean isOpen;
    private Set<OrderResponse> orderResponse;

    public DailyOrderListResponse() {
    }

    public DailyOrderListResponse(Long id, String uniqueId, Double dailyIncome,
                                  Boolean isOpen, Set<OrderResponse> orderResponse) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.dailyIncome = dailyIncome;
        this.isOpen = isOpen;
        this.orderResponse = orderResponse;
    }

    public Long getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public Double getDailyIncome() {
        return dailyIncome;
    }

    public Boolean isOpen() {
        return isOpen;
    }

    public Set<OrderResponse> getOrderResponse() {
        return orderResponse;
    }
}
