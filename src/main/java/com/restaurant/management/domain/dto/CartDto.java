package com.restaurant.management.domain.dto;

import com.restaurant.management.domain.Customer;
import com.restaurant.management.domain.LineItem;

import java.util.List;

public class CartDto {

    private Long id;
    private Boolean isOpen;
    private Customer customer;
    private List<LineItem> lineItems;

    public CartDto(Long id,
                   Boolean isOpen,
                   Customer customer,
                   List<LineItem> lineItems) {
        this.id = id;
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

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
