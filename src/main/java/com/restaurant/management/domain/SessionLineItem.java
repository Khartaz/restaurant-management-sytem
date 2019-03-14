package com.restaurant.management.domain;

import javax.persistence.*;

@Entity
@Table(name = "session_line_items")
public class SessionLineItem extends AbstractLineItem {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Product product;

    public SessionLineItem() {
    }

    public SessionLineItem(Long id, Integer quantity, Double price, Product product) {
        super(id, quantity, price);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
