package com.restaurant.management.domain.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class DailyOrderListDto {
    private Long id;
    private String uniqueId;
    private Double dailyIncome;
    private Boolean isOpen;
    private Set<OrderDto> ordersDto = new LinkedHashSet<>();

    public DailyOrderListDto() {
    }

    public DailyOrderListDto(Long id, String uniqueId,
                             Double dailyIncome, Boolean isOpen,
                             Set<OrderDto> ordersDto) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.dailyIncome = dailyIncome;
        this.isOpen = isOpen;
        this.ordersDto = ordersDto;
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

    public Set<OrderDto> getOrdersDto() {
        return ordersDto;
    }

}
