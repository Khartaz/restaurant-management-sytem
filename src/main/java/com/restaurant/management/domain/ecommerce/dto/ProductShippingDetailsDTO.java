package com.restaurant.management.domain.ecommerce.dto;

public final class ProductShippingDetailsDTO {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private Double width;
    private Double height;
    private Double depth;
    private Double weight;
    private Double extraShippingFee;

    public ProductShippingDetailsDTO() {
    }

    public ProductShippingDetailsDTO(Long createdAt, Long updatedAt, String createdByUserId,
                                     String updatedByUserId, Long id, Double width,
                                     Double height, Double depth, Double weight,
                                     Double extraShippingFee) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;
        this.extraShippingFee = extraShippingFee;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public String getUpdatedByUserId() {
        return updatedByUserId;
    }

    public Long getId() {
        return id;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public Double getDepth() {
        return depth;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getExtraShippingFee() {
        return extraShippingFee;
    }
}
