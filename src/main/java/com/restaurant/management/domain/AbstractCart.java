package com.restaurant.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@MappedSuperclass
@JsonIgnoreProperties(allowGetters = true)
public abstract class AbstractCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "isOpen")
    private Boolean isOpen;

    public AbstractCart() {
    }

    public AbstractCart(Long id, String uniqueId, Boolean isOpen) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.isOpen = isOpen;
    }

    public AbstractCart(String uniqueId, Boolean isOpen) {
        this.uniqueId = uniqueId;
        this.isOpen = isOpen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Boolean isOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
