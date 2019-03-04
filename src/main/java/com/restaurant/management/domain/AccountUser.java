package com.restaurant.management.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account_users")
public class AccountUser extends AbstractUser {

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

    public AccountUser() {
    }

    public AccountUser(String name, String lastname, String email,
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

    public AccountUser(Long id, String name, String lastname, String email,
                         String username, String userUniqueId, Boolean isActive, Set<Role> roles) {
        super(id, name, lastname, email);
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

    public static class AccountUserBuilder {
        private String name;
        private String lastname;
        private String email;
        private String username;
        private String userUniqueId;
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

        public AccountUserBuilder setUserUniqueId(String userUniqueId) {
            this.userUniqueId = userUniqueId;
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
                    this.username, this.userUniqueId, this.password,
                    this.emailVerificationToken, this.isActive, this.roles);
        }
    }
}
