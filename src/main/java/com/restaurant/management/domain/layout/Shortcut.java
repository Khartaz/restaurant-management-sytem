package com.restaurant.management.domain.layout;

import com.restaurant.management.domain.ecommerce.User;

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
    private User user;

    public Shortcut() {

    }

    public Shortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public Shortcut(String shortcut, User user) {
        this.shortcut = shortcut;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Shortcut{" +
                "id=" + id +
                ", shortcut='" + shortcut + '\'' +
                ", accountUser=" + user +
                '}';
    }
}
