package com.restaurant.management.web.response;

import java.util.List;

public final class CartResponse {
    private Long id;
    private Boolean isOpen;
    private Double totalPrice;
    private CustomerResponse customer;
    private List<LineItemResponse> lineItems;

    public CartResponse() {
    }

    public CartResponse(Long id,
                        Boolean isOpen,
                        Double totalPrice,
                        CustomerResponse customer,
                        List<LineItemResponse> lineItems) {
        this.id = id;
        this.isOpen = isOpen;
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.lineItems = lineItems;
    }

    public Long getId() {
        return id;
    }

    public Boolean isOpen() {
        return isOpen;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    public List<LineItemResponse> getLineItems() {
        return lineItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }
}
