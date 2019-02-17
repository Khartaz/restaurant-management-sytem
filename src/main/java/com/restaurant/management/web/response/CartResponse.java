package com.restaurant.management.web.response;

import java.util.List;

public class CartResponse {
    private String cartNumber;
    private Boolean isOpen;
    private CustomerResponse customer;
    private List<LineItemResponse> lineItems;

    public CartResponse(String cartNumber,
                        Boolean isOpen,
                        CustomerResponse customer,
                        List<LineItemResponse> lineItems) {
        this.cartNumber = cartNumber;
        this.isOpen = isOpen;
        this.customer = customer;
        this.lineItems = lineItems;
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

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    public List<LineItemResponse> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItemResponse> lineItems) {
        this.lineItems = lineItems;
    }
}
