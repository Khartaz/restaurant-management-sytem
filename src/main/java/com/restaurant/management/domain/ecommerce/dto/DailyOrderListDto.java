package com.restaurant.management.domain.ecommerce.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public final class DailyOrderListDto {
    private Long id;
    private Double dailyIncome;
    private Integer numberOfOrders;
    private Boolean isOpen;
    private Set<OrderDto> ordersDto = new LinkedHashSet<>();

    public DailyOrderListDto() {
    }

    public DailyOrderListDto(Long id, Double dailyIncome,
                             Integer numberOfOrders, Boolean isOpen,
                             Set<OrderDto> ordersDto) {
        this.id = id;
        this.dailyIncome = dailyIncome;
        this.numberOfOrders = numberOfOrders;
        this.isOpen = isOpen;
        this.ordersDto = ordersDto;
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

    public Set<OrderDto> getOrdersDto() {
        return ordersDto;
    }

}
