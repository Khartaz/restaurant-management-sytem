package com.restaurant.management.domain.layout.settings.theme;

import javax.persistence.*;

@Entity
@Table(name = "theme")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "main")
    private String main = "defaultDark";

    @Column(name = "navbar")
    private String navbar = "defaultDark";

    @Column(name = "toolbar")
    private String toolbar = "defaultDark";

    @Column(name = "footer")
    private String footer = "defaultDark";

    public Theme() {

    }

    public Theme(String main, String navbar, String toolbar, String footer) {
        this.main = main;
        this.navbar = navbar;
        this.toolbar = toolbar;
        this.footer = footer;
    }

    public Long getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getNavbar() {
        return navbar;
    }

    public void setNavbar(String navbar) {
        this.navbar = navbar;
    }

    public String getToolbar() {
        return toolbar;
    }

    public void setToolbar(String toolbar) {
        this.toolbar = toolbar;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }
}