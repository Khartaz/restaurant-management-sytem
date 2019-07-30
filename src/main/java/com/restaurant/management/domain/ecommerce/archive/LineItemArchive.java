package com.restaurant.management.domain.ecommerce.archive;

import com.restaurant.management.domain.ecommerce.AbstractLineItem;
import com.restaurant.management.domain.ecommerce.Company;

import javax.persistence.*;

@Entity
@Table(name = "line_items_archive")
public class LineItemArchive extends AbstractLineItem {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProductArchive product;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Company company;

    public LineItemArchive() {
    }

    public LineItemArchive(Integer quantity, Double price, ProductArchive product) {
        super(quantity, price);
        this.product = product;
    }

    public LineItemArchive(Integer quantity, Double price,
                           ProductArchive product, Company company) {
        super(quantity, price);
        this.product = product;
        this.company = company;
    }

    public ProductArchive getProduct() {
        return product;
    }

    public void setProduct(ProductArchive product) {
        this.product = product;
    }

    public Company getRestaurantInfo() {
        return company;
    }

    public void setRestaurantInfo(Company company) {
        this.company = company;
    }


    public static class LineItemArchiveBuilder {
        private Integer quantity;
        private Double price;
        private ProductArchive productArchive;
        private Company company;

        public LineItemArchiveBuilder setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public LineItemArchiveBuilder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public LineItemArchiveBuilder setProductArchive(ProductArchive productArchive) {
            this.productArchive = productArchive;
            return this;
        }

        public LineItemArchiveBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public LineItemArchive build() {
            return new LineItemArchive(this.quantity, this.price, this.productArchive, this.company);
        }
    }

}
