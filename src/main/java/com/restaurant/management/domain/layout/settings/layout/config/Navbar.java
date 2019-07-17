package com.restaurant.management.domain.layout.settings.layout.config;

import javax.persistence.*;

@Entity
@Table(name = "navbar")
public class Navbar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "display")
    private Boolean display = Boolean.TRUE;

    @Column(name = "folded")
    private Boolean folded = Boolean.TRUE;

    @Column(name = "position")
    private String position = "left";

    public Navbar() {

    }

    public Navbar(Boolean display, Boolean folded, String position) {
        this.display = display;
        this.folded = folded;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public Boolean getFolded() {
        return folded;
    }

    public void setFolded(Boolean folded) {
        this.folded = folded;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
