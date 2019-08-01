package com.restaurant.management.domain.ecommerce;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Audited
public class Product extends AbstractProduct {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Company company;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ProductShippingDetails productShippingDetails;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ProductInventory productInventory;

    public Product() {
    }

    public Product(Long createdAt, Long updatedAt,
                   String createdByUserId, String updatedByUserId,
                   Long id, String name, String category,
                   Double price) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name, category, price);
    }

    public Product(Long createdAt, Long updatedAt, String createdByUserId, String updatedByUserId,
                   Long id, String name, Double price, String description,
                   ProductShippingDetails productShippingDetails,
                   ProductInventory productInventory) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, name,  price, description);
        this.productShippingDetails = productShippingDetails;
        this.productInventory = productInventory;
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

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public ProductShippingDetails getProductShippingDetails() {
        return productShippingDetails;
    }

    public void setProductShippingDetails(ProductShippingDetails productShippingDetails) {
        this.productShippingDetails = productShippingDetails;
    }

    public ProductInventory getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(ProductInventory productInventory) {
        this.productInventory = productInventory;
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
