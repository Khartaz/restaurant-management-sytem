package com.restaurant.management.domain.ecommerce.dto;

public final class ProductFormDTO {
    private String createdAt;
    private String updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String name;
    private Double priceTaxIncl;
    private String description;
    private String sku;
    private Double quantity;
    private Double width;
    private Double height;
    private Double depth;
    private Double weight;
    private Double extraShippingFee;

    public ProductFormDTO() {
    }

    public ProductFormDTO(String createdAt, String updatedAt, String createdByUserId,
                          String updatedByUserId, Long id, String name, Double priceTaxIncl,
                          String description, String sku, Double quantity, Double width,
                          Double height, Double depth, Double weight, Double extraShippingFee) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.priceTaxIncl = priceTaxIncl;
        this.description = description;
        this.sku = sku;
        this.quantity = quantity;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;
        this.extraShippingFee = extraShippingFee;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
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

    public String getName() {
        return name;
    }

    public Double getPriceTaxIncl() {
        return priceTaxIncl;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    public Double getQuantity() {
        return quantity;
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
