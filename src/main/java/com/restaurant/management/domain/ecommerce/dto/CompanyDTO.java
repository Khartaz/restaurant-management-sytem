package com.restaurant.management.domain.ecommerce.dto;

public final class CompanyDTO {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String name;
    private AddressDTO address;

    public CompanyDTO() {
    }

    public CompanyDTO(Long createdAt, Long updatedAt, String createdByUserId,
                      String updatedByUserId, Long id, String name,
                      AddressDTO address) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.address = address;
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

    public AddressDTO getAddress() {
        return address;
    }
}
