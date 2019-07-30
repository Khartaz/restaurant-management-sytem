package com.restaurant.management.domain.ecommerce.dto;

public final class CompanyDto {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String name;
    private AddressDto addressDto;

    public CompanyDto() {
    }

    public CompanyDto(Long createdAt, Long updatedAt, String createdByUserId,
                      String updatedByUserId, Long id, String name,
                      AddressDto addressDto) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.addressDto = addressDto;
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

    public AddressDto getAddressDto() {
        return addressDto;
    }
}
