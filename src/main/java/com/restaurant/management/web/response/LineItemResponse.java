package com.restaurant.management.web.response;

public class LineItemResponse {
    private Long id;
    private ProductResponse product;
    private Integer quantity;
    private Double price;

    public LineItemResponse(Long id,
                            ProductResponse product,
                            Integer quantity, Double price) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
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
