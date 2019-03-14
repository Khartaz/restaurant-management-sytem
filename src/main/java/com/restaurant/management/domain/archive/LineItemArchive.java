package com.restaurant.management.domain;

import javax.persistence.*;

@Entity
@Table(name = "line_items_archive")
public class LineItemArchive extends AbstractLineItem {

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private ProductArchive product;

    public LineItemArchive() {
    }

    public LineItemArchive(Integer quantity, Double price, ProductArchive product) {
        super(quantity, price);
        this.product = product;
    }

    public ProductArchive getProduct() {
        return product;
    }

    public void setProduct(ProductArchive product) {
        this.product = product;
    }
}
