package com.restaurant.management.domain.ecommerce.dto;

public final class LineItemDto {
    private Long id;
    private ProductDto productDto;
    private Integer quantity;
    private Double price;

    public LineItemDto() {
    }

    public LineItemDto(Long id, Integer quantity, Double price, ProductDto productDto) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.productDto = productDto;
    }

    public LineItemDto(Integer quantity, Double price, ProductDto productDto) {
        this.quantity = quantity;
        this.price = price;
        this.productDto = productDto;
    }

    public LineItemDto(Long id, ProductDto productDto, Integer quantity) {
        this.id = id;
        this.productDto = productDto;
        this.quantity = quantity;
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
