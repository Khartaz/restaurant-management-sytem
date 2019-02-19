package com.restaurant.management.domain.dto;

public class LineItemDto {

    private ProductDto productDto;
    private Integer quantity;
    private Double price;

    public LineItemDto(ProductDto productDto, Integer quantity, Double price) {
        this.productDto = productDto;
        this.quantity = quantity;
        this.price = price;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }
}
