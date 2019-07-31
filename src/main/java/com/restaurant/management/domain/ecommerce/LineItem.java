package com.restaurant.management.domain.ecommerce;

import javax.persistence.*;

@Entity
@Table(name = "line_items")
public class LineItem extends AbstractLineItem {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Company company;

    public LineItem() {
    }

    public LineItem(Long id, Integer quantity, Double price, Product product) {
        super(id, quantity, price);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
