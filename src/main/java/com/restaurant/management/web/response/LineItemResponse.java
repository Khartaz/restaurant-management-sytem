package com.restaurant.management.web.response;

public class LineItemResponse {

    private ProductResponse product;
    private Integer quantity;
    private Double price;

    public LineItemResponse(ProductResponse product,
                            Integer quantity, Double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }
}
