package com.restaurant.management.domain.ecommerce;

import javax.persistence.*;

@Entity
@Table(name = "line_items_ordered")
public class LineItemOrdered extends AbstractLineItem {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProductOrdered productOrdered;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Company company;

    public LineItemOrdered() {
    }

    public LineItemOrdered(Integer quantity, Double price, ProductOrdered productOrdered) {
        super(quantity, price);
        this.productOrdered = productOrdered;
    }

    public LineItemOrdered(Integer quantity, Double price,
                           ProductOrdered productOrdered, Company company) {
        super(quantity, price);
        this.productOrdered = productOrdered;
        this.company = company;
    }

    public ProductOrdered getProductOrdered() {
        return productOrdered;
    }

    public void setProductOrdered(ProductOrdered productOrdered) {
        this.productOrdered = productOrdered;
    }

    public Company getRestaurantInfo() {
        return company;
    }

    public void setRestaurantInfo(Company company) {
        this.company = company;
    }


    public static class LineItemOrderedBuilder {
        private Integer quantity;
        private Double price;
        private ProductOrdered productOrdered;
        private Company company;

        public LineItemOrderedBuilder setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public LineItemOrderedBuilder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public LineItemOrderedBuilder setProductOrdered(ProductOrdered productOrdered) {
            this.productOrdered = productOrdered;
            return this;
        }

        public LineItemOrderedBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public LineItemOrdered build() {
            return new LineItemOrdered(this.quantity, this.price, this.productOrdered, this.company);
        }
    }

}
