package com.restaurant.management.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account_users")
public class AccountUser extends AbstractAdmin {

    public AccountUser() {
    }

    public AccountUser(String name, String lastname, String email, String username,
                       String userUniqueId, String password, String emailVerificationToken,
                       Boolean isActive, Set<Role> roles) {
        super(name, lastname, email,
              username, userUniqueId, password,
              emailVerificationToken, isActive, roles);
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public String getUserUniqueId() {
        return super.getUserUniqueId();
    }

    @Override
    public void setUserUniqueId(String userUniqueId) {
        super.setUserUniqueId(userUniqueId);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getLastname() {
        return super.getLastname();
    }

    @Override
    public void setLastname(String lastname) {
        super.setLastname(lastname);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
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
