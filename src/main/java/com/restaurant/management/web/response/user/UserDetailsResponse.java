package com.restaurant.management.web.response.user;

public final class UserDetailsResponse {
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;
    private Long id;
    private String role;
    private String name;
    private String lastname;
    private String photoURL;
    private String email;
    private String phoneNumber;

    public UserDetailsResponse() {
    }

    public UserDetailsResponse(Long createdAt, Long updatedAt, String createdByUserId,
                               String updatedByUserId, Long id, String role,
                               String name, String lastname, String photoURL,
                               String email, String phoneNumber) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
        this.id = id;
        this.role = role;
        this.name = name;
        this.lastname = lastname;
        this.photoURL = photoURL;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
