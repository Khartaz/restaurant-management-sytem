package com.restaurant.management.domain;

import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account_users")
public class AccountUser extends AbstractUser {

    @Size(max = 100)
    @Column(name = "username", unique = true)
    private String username;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="account_users_roles",
            joinColumns = @JoinColumn( name="user_id"),
            inverseJoinColumns = @JoinColumn( name="role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RestaurantInfo restaurantInfo;

    public AccountUser() {
    }

    public AccountUser(String name, String lastname, String email,
                       String username, String password,
                       String emailVerificationToken,
                       Boolean isActive, Set<Role> roles) {
        super(name, lastname, email);
        this.username = username;
        this.password = password;
        this.emailVerificationToken = emailVerificationToken;
        this.isActive = isActive;
        this.roles = roles;
    }

    public AccountUser(Long createdAt, Long updatedAt, String createdBy, String updatedBy,
                       Long id, String name, String lastname, String email,
                       String username, String emailVerificationToken,
                       Boolean isActive, Set<Role> roles) {
        super(createdAt, updatedAt, createdBy, updatedBy, id, name, lastname, email);
        this.username = username;
        this.emailVerificationToken = emailVerificationToken;
        this.isActive = isActive;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Boolean isActive() {
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

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }

    public static class AccountUserBuilder {
        private String name;
        private String lastname;
        private String email;
        private String username;
        private String password;
        private String emailVerificationToken;
        private Boolean isActive;
        private Set<Role> roles = new HashSet<>();

        public AccountUserBuilder setName(String name) {
            this.name = name;
            return this;
        }
        public AccountUserBuilder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public AccountUserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AccountUserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public AccountUserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public AccountUserBuilder setEmailVerificationToken(String emailVerificationToken) {
            this.emailVerificationToken = emailVerificationToken;
            return this;
        }

        public AccountUserBuilder setIsActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public AccountUserBuilder setRoles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public AccountUser build() {
            return new AccountUser(this.name, this.lastname, this.email,
                    this.username, this.password,
                    this.emailVerificationToken, this.isActive, this.roles);
        }
    }
}
