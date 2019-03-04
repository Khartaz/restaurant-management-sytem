package com.restaurant.management.domain.dto;

public class LineItemDto {
    private Long id;
    private ProductDto productDto;
    private Integer quantity;
    private Double price;

    public LineItemDto(Long id, ProductDto productDto, Integer quantity, Double price) {
        this.id = id;
        this.productDto = productDto;
        this.quantity = quantity;
        this.price = price;
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
