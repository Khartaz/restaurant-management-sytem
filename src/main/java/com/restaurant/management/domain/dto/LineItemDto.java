package com.restaurant.management.domain.dto;

public class LineItemDto {
    private Long id;
    private ProductDto productDto;
    private Integer quantity;
    private Double price;

    public LineItemDto(Long id, Integer quantity, Double price, ProductDto productDto) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.productDto = productDto;
    }

    public Long getId() {
        return id;
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
