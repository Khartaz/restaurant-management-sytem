package com.restaurant.management.domain.ecommerce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@JsonIgnoreProperties(allowGetters = true)
public abstract class AbstractUser extends AbstractAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 40)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Size(max = 40)
    @Column(name = "lastname")
    private String lastname;

    @NotBlank
    @Size(max = 40)
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    @Size(max = 25)
    private String phoneNumber;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public AbstractUser() {
    }

    public AbstractUser(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                        Long id, String name, String lastname, String email, String phoneNumber) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId);
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public AbstractUser(String name, String lastname, String email, String phoneNumber) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
