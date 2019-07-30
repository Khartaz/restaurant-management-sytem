package com.restaurant.management.domain.ecommerce;

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

    @Column(name = "total_price")
    private Double totalPrice;

    public AbstractCart() {
    }

    public AbstractCart(Long id, Boolean isOpen, Double totalPrice) {
        this.id = id;
        this.isOpen = isOpen;
        this.totalPrice = totalPrice;
    }

    public AbstractCart(Boolean isOpen, Double totalPrice) {
        this.isOpen = isOpen;
        this.totalPrice = totalPrice;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
