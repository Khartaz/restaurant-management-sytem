package com.restaurant.management.web.response;

import java.util.List;

public class CartResponse {
    private Long id;
    private String cartNumber;
    private Boolean isOpen;
    private CustomerResponse customer;
    private List<LineItemResponse> lineItems;

    public CartResponse(Long id,
                        String cartNumber,
                        Boolean isOpen,
                        CustomerResponse customer,
                        List<LineItemResponse> lineItems) {
        this.id = id;
        this.cartNumber = cartNumber;
        this.isOpen = isOpen;
        this.customer = customer;
        this.lineItems = lineItems;
    }

    public Long getId() {
        return id;
    }

    public String getCartNumber() {
        return cartNumber;
    }

    public Boolean getOpen() {
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
}
