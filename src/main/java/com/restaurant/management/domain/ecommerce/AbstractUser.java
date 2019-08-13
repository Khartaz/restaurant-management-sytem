package com.restaurant.management.domain.ecommerce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
@JsonIgnoreProperties(allowGetters = true)
public abstract class AbstractUser extends AbstractAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 80)
    @Column(name = "name")
    @NotNull
    private String name;

    @Size(max = 80)
    @Column(name = "lastName")
    private String lastName;

    @Size(max = 80)
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    @Size(max = 25)
    private String phone;

    @Column(name = "jobTitle")
    private String jobTitle;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public AbstractUser() {
    }

    public AbstractUser(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                        Long id, String name, String lastName, String email, String phone, String jobTitle) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId);
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
    }

    public AbstractUser(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                        Long id, String name, String lastName, String email, String phone) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId);
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public AbstractUser(String name, String lastName, String email, String phone, String jobTitle) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
    }

    public AbstractUser(String name, String lastName, String email, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
