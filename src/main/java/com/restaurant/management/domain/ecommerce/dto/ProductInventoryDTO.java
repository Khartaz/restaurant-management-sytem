package com.restaurant.management.domain.ecommerce.dto;

public class ProductInventoryDTO {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String sku;
    private Double quantity;

    public ProductInventoryDTO() {

    }

    public ProductInventoryDTO(Long createdAt, Long updatedAt,
                               String createdByUserId, String updatedByUserId,
                               Long id, String sku, Double quantity) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.sku = sku;
        this.quantity = quantity;
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

    public String getSku() {
        return sku;
    }

    public Double getQuantity() {
        return quantity;
    }
}
