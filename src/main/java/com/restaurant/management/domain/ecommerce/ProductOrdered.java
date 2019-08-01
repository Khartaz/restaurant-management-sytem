package com.restaurant.management.domain.ecommerce;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "products_ordered")
@Audited
public class ProductOrdered extends AbstractProduct {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Company company;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ProductOrderedShippingDetails productOrderedShippingDetails;

    public ProductOrdered() {
    }

    public ProductOrdered(Long createdAt, Long updatedAt,
                          String createdByUserId, String updatedByUserId,
                          Long id, String name, Double price, String description,
                          ProductOrderedShippingDetails productOrderedShippingDetails) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, price, description);
        this.productOrderedShippingDetails = productOrderedShippingDetails;
    }

    public ProductOrdered(String name, String category, Double price, Company company) {
        super(name, category, price);
        this.company = company;
    }

    public ProductOrdered(String name, String category, Double price) {
        super(name, category, price);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ProductOrderedShippingDetails getProductOrderedShippingDetails() {
        return productOrderedShippingDetails;
    }

    public void setProductOrderedShippingDetails(ProductOrderedShippingDetails productOrderedShippingDetails) {
        this.productOrderedShippingDetails = productOrderedShippingDetails;
    }

    public static class ProductOrderedBuilder {
        private String name;
        private String category;
        private Double price;
        private Company company;

        public ProductOrderedBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductOrderedBuilder setCategory(String category) {
            this.category = category;
            return this;
        }

        public ProductOrderedBuilder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public ProductOrderedBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public ProductOrdered build() {
            return new ProductOrdered(this.name, this.category, this.price, this.company);
        }
    }
}
