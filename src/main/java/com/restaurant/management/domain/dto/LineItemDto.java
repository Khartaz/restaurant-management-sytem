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

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
