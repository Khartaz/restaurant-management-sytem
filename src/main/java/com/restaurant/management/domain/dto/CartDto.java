package com.restaurant.management.domain.dto;

import java.util.List;

public final class CartDto {

    private Long id;
    private Boolean isOpen;
    private CustomerDto customer;
    private List<LineItemDto> lineItems;

    public CartDto() {
    }

    public CartDto(Long id,
                   Boolean isOpen,
                   CustomerDto customer,
                   List<LineItemDto> lineItems) {
        this.id = id;
        this.isOpen = isOpen;
        this.customer = customer;
        this.lineItems = lineItems;
    }

    public CartDto(Boolean isOpen,
                   CustomerDto customer) {
        this.isOpen = isOpen;
        this.customer = customer;
    }

    public CartDto(Boolean isOpen,
                   CustomerDto customer,
                   List<LineItemDto> lineItems) {
        this.isOpen = isOpen;
        this.customer = customer;
        this.lineItems = lineItems;
    }

    public Long getId() {
        return id;
    }


    public Boolean isOpen() {
        return isOpen;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public List<LineItemDto> getLineItems() {
        return lineItems;
    }
}
