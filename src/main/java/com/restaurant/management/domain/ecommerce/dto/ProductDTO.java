package com.restaurant.management.domain.ecommerce.dto;

public final class ProductDTO {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String name;
    private Double priceTaxIncl;
    private String description;
    private ProductShippingDetailsDTO productShippingDetailsDTO;
    private ProductInventoryDTO productInventoryDTO;

    public ProductDTO() {
    }

    public ProductDTO(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                      Long id, String name, Double priceTaxIncl, String description,
                      ProductShippingDetailsDTO productShippingDetailsDTO,
                      ProductInventoryDTO productInventoryDTO) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.priceTaxIncl = priceTaxIncl;
        this.description = description;
        this.productShippingDetailsDTO = productShippingDetailsDTO;
        this.productInventoryDTO = productInventoryDTO;
    }

    public ProductDTO(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                      Long id, String name, Double priceTaxIncl, String description,
                      ProductShippingDetailsDTO productShippingDetailsDTO) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.priceTaxIncl = priceTaxIncl;
        this.description = description;
        this.productShippingDetailsDTO = productShippingDetailsDTO;
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

    public String getName() {
        return name;
    }

    public Double getPriceTaxIncl() {
        return priceTaxIncl;
    }

    public String getDescription() {
        return description;
    }

    public ProductShippingDetailsDTO getProductShippingDetailsDTO() {
        return productShippingDetailsDTO;
    }

    public ProductInventoryDTO getProductInventoryDTO() {
        return productInventoryDTO;
    }
}
