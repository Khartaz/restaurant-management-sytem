package com.restaurant.management.domain.layout;

import com.restaurant.management.domain.AccountUser;

import javax.persistence.*;

@Entity
@Table(name = "shortcut")
public class Shortcut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shortcut")
    private String shortcut;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private AccountUser accountUser;

    public Shortcut() {

    }

    public Shortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public Shortcut(String shortcut, AccountUser accountUser) {
        this.shortcut = shortcut;
        this.accountUser = accountUser;
    }

    public Long getId() {
        return id;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public AccountUser getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(AccountUser accountUser) {
        this.accountUser = accountUser;
    }

    @Override
    public String toString() {
        return "Shortcut{" +
                "id=" + id +
                ", shortcut='" + shortcut + '\'' +
                ", accountUser=" + accountUser +
                '}';
    }
}
