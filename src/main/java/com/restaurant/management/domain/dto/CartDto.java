package com.restaurant.management.domain.dto;

import java.util.List;

public class CartDto {

    private Long id;
    private String cartNumber;
    private Boolean isOpen;
    private CustomerDto customer;
    private List<LineItemDto> lineItems;

    public CartDto(Long id,
                   String cartNumber,
                   Boolean isOpen,
                   CustomerDto customer,
                   List<LineItemDto> lineItems) {
        this.id = id;
        this.cartNumber = cartNumber;
        this.isOpen = isOpen;
        this.customer = customer;
        this.lineItems = lineItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartNumber() {
        return cartNumber;
    }

    public void setCartNumber(String cartNumber) {
        this.cartNumber = cartNumber;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public List<LineItemDto> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItemDto> lineItems) {
        this.lineItems = lineItems;
    }
}
