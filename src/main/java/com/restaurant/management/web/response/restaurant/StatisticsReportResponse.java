package com.restaurant.management.web.response.restaurant;

public class StatisticsReportResponse {
    private Integer ordersToday;
    private Double incomeToday;
    private Integer productsToday;

    public StatisticsReportResponse(Integer ordersToday, Double incomeToday, Integer productsToday) {
        this.ordersToday = ordersToday;
        this.incomeToday = incomeToday;
        this.productsToday = productsToday;
    }

    public Integer getOrdersToday() {
        return ordersToday;
    }

    public Double getIncomeToday() {
        return incomeToday;
    }

    public Integer getProductsToday() {
        return productsToday;
    }
}
