package com.restaurant.management.domain.layout.settings.layout;

import com.restaurant.management.domain.layout.settings.layout.config.Footer;
import com.restaurant.management.domain.layout.settings.layout.config.Toolbar;
import com.restaurant.management.domain.layout.settings.layout.config.Navbar;

import javax.persistence.*;

@Entity
@Table(name = "config")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "scroll")
    private String scroll = "content";

    @Column(name = "mode")
    private String mode = "fullwidth";

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Navbar navbar;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Toolbar toolbar;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Footer footer;

    public Config() {
    }

    public Config(String scroll, String mode, Navbar navbar,
                  Toolbar toolbar, Footer footer) {
        this.scroll = scroll;
        this.mode = mode;
        this.navbar = navbar;
        this.toolbar = toolbar;
        this.footer = footer;
    }

    public Config(Navbar navbar, Toolbar toolbar, Footer footer) {
        this.navbar = navbar;
        this.toolbar = toolbar;
        this.footer = footer;
    }

    public Long getId() {
        return id;
    }

    public String getScroll() {
        return scroll;
    }

    public void setScroll(String scroll) {
        this.scroll = scroll;
    }

    public Navbar getNavbar() {
        return navbar;
    }

    public void setNavbar(Navbar navbar) {
        this.navbar = navbar;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
