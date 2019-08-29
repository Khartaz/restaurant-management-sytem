package com.restaurant.management.domain.layout;

import com.restaurant.management.domain.layout.settings.Layout;
import com.restaurant.management.domain.layout.settings.theme.Theme;

import javax.persistence.*;

@Entity
@Table(name = "settings")
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "photo_url")
    private String photoURL;

    @Column(name = "custom_scrollbars")
    private Boolean customScrollbars = Boolean.TRUE;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Layout layout;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Theme theme;

    public Settings() {

    }

    public Settings(Layout layout, Theme theme) {
        this.layout = layout;
        this.theme = theme;
    }

    public Settings(String photoURL, Boolean customScrollbars, Layout layout, Theme theme) {
        this.photoURL = photoURL;
        this.customScrollbars = customScrollbars;
        this.layout = layout;
        this.theme = theme;
    }

    public Long getId() {
        return id;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Boolean getCustomScrollbars() {
        return customScrollbars;
    }

    public void setCustomScrollbars(Boolean customScrollbars) {
        this.customScrollbars = customScrollbars;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

}
