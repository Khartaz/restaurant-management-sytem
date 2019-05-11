package com.restaurant.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@MappedSuperclass
@JsonIgnoreProperties(allowGetters = true)
public abstract class AbstractCart extends AbstractAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "isOpen")
    private Boolean isOpen;

    public AbstractCart() {
    }

    public AbstractCart(Long id, Boolean isOpen) {
        this.id = id;
        this.isOpen = isOpen;
    }

    public AbstractCart(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Long getId() {
        return id;
    }

    public Boolean isOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
