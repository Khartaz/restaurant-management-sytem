package com.restaurant.management.web.response;

public class LineItemResponse {
    private Long id;
    private ProductResponse product;
    private Integer quantity;
    private Double price;

    public LineItemResponse() {
    }

    public LineItemResponse(Long id, Integer quantity,
                            Double price, ProductResponse product) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
    }

    public Long getId() {
        return id;
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
