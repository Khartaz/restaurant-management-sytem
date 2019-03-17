package com.restaurant.management.web.response;

import java.util.Set;

public class DailyOrderListResponse {
    private Long id;
    private String uniqueId;
    private Double dailyIncome;
    private Boolean isOpened;
    private Set<OrderResponse> orderResponse;

    public DailyOrderListResponse(Long id, String uniqueId, Double dailyIncome,
                                  Boolean isOpened, Set<OrderResponse> orderResponse) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.dailyIncome = dailyIncome;
        this.isOpened = isOpened;
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

    public Boolean getOpened() {
        return isOpened;
    }

    public Set<OrderResponse> getOrderResponse() {
        return orderResponse;
    }
}
