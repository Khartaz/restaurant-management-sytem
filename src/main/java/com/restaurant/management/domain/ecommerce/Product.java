package com.restaurant.management.domain.ecommerce;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Audited
public class Product extends AbstractProduct {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Company company;

    public Product() {
    }

    public Product(Long createdAt, Long updatedAt,
                   String createdByUserId, String updatedByUserId,
                   Long id, String name, String category,
                   Double price) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, category, price);
    }

    public Product(Long id, String name, String category,
                   Double price) {
        super(id, name, category, price);
    }

    public Product(String name, String category,
                   Double price, Company company) {
        super(name, category, price);
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public static class ProductBuilder {
        private String name;
        private String category;
        private Double price;
        private Company company;

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setCategory(String category) {
            this.category = category;
            return this;
        }

        public ProductBuilder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public ProductBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public Product build() {
            return new Product(this.name, this.category,
                    this.price, this.company);
        }
    }

}
