package com.restaurant.management.domain.ecommerce.archive;

import com.restaurant.management.domain.ecommerce.AbstractProduct;
import com.restaurant.management.domain.ecommerce.Company;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products_archive")
@Audited
public class ProductArchive extends AbstractProduct {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Company company;

    public ProductArchive() {
    }

    public ProductArchive(Long createdAt, Long updatedAt,
                          String createdByUserId, String updatedByUserId,
                          Long id, String name, String category,
                          Double price) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, category, price);
    }

    public ProductArchive(String name, String category, Double price, Company company) {
        super(name, category, price);
        this.company = company;
    }

    public ProductArchive(String name, String category, Double price) {
        super(name, category, price);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public static class ProductArchiveBuilder {
        private String name;
        private String category;
        private Double price;
        private Company company;

        public ProductArchiveBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductArchiveBuilder setCategory(String category) {
            this.category = category;
            return this;
        }

        public ProductArchiveBuilder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public ProductArchiveBuilder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public ProductArchive build() {
            return new ProductArchive(this.name, this.category, this.price, this.company);
        }
    }
}
