package com.restaurant.management.domain.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class DailyOrderListDto {
    private Long id;
    private String uniqueId;
    private Double dailyIncome;
    private Boolean isOpened;
    private Set<OrderDto> ordersDto = new LinkedHashSet<>();

    public DailyOrderListDto(Long id, String uniqueId,
                             Double dailyIncome, Boolean isOpened,
                             Set<OrderDto> ordersDto) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.dailyIncome = dailyIncome;
        this.isOpened = isOpened;
        this.ordersDto = ordersDto;
    }

    public DailyOrderListDto() {
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

    public Set<OrderDto> getOrdersDto() {
        return ordersDto;
    }

}
