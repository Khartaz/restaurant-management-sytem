package com.restaurant.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@JsonIgnoreProperties(allowGetters = true)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractAdmin extends AbstractUser {

    @NotBlank
    @Size(max = 15)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(max = 40)
    @Column(name = "user_unique_id")
    private String userUniqueId;

    @NotBlank
    @Size(max = 100)
    @Column(name = "password")
    private String password;

    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @Column(name = "encrypted_password")
    private String encryptedPassword;

    @Column(name = "email_verification_token")
    private String emailVerificationToken;

    @Column(name = "isActive")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public AbstractAdmin() {
    }

    public AbstractAdmin(Long id, String name, String lastname, String email,
                         String username, String userUniqueId, String password,
                         String emailVerificationToken, Boolean isActive, Set<Role> roles) {
        super(id, name, lastname, email);
        this.username = username;
        this.userUniqueId = userUniqueId;
        this.password = password;
        this.emailVerificationToken = emailVerificationToken;
        this.isActive = isActive;
        this.roles = roles;
    }

    public AbstractAdmin(String name, String lastname, String email,
                         String username, String userUniqueId, String password,
                         String emailVerificationToken, Boolean isActive, Set<Role> roles) {
        super(name, lastname, email);
        this.username = username;
        this.userUniqueId = userUniqueId;
        this.password = password;
        this.emailVerificationToken = emailVerificationToken;
        this.isActive = isActive;
        this.roles = roles;
    }

    public AbstractAdmin(String name, String lastname, String email,
                         String username, String userUniqueId, Boolean isActive, Set<Role> roles) {
        super(name, lastname, email);
        this.username = username;
        this.userUniqueId = userUniqueId;
        this.isActive = isActive;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserUniqueId() {
        return userUniqueId;
    }

    public void setUserUniqueId(String userUniqueId) {
        this.userUniqueId = userUniqueId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
