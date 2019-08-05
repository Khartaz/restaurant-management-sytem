package com.restaurant.management.web.response;

public final class CustomerResponse {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private AddressResponse addressResponse;

    public CustomerResponse() {
    }

    public CustomerResponse(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                            Long id, String name, String lastName, String email, String phone,
                            AddressResponse addressResponse) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.addressResponse = addressResponse;
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

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public AddressResponse getAddressResponse() {
        return addressResponse;
    }
}
