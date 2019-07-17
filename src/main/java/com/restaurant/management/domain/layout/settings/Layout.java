package com.restaurant.management.domain.layout.settings;

import com.restaurant.management.domain.layout.settings.layout.Config;

import javax.persistence.*;

@Entity
@Table(name = "layout")
public class Layout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "style")
    private String style = "layout1";

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Config config;

    public Layout() {

    }

    public Layout(String style, Config config) {
        this.style = style;
        this.config = config;
    }

    public Layout(Config config) {
        this.config = config;
    }

    public Long getId() {
        return id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
