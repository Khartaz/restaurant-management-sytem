package com.restaurant.management.web.response;

public final class CustomerResponse {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String phoneNumber;
    private AddressResponse addressResponse;

    public CustomerResponse() {
    }

    public CustomerResponse(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                            Long id, String name, String lastname, String email, String phoneNumber,
                            AddressResponse addressResponse) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AddressResponse getAddressResponse() {
        return addressResponse;
    }
}
