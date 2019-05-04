package com.restaurant.management.domain;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Audited
public class Product extends AbstractProduct {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    public Product() {
    }

    public Product(Long createdAt, Long updatedAt,
                   String createdBy, String updatedBy,
                   Long id, String uniqueId, String name, String category,
                   Double price, List<Ingredient> ingredients) {
        super(createdAt, updatedAt, createdBy, updatedBy, id, uniqueId, name, category, price);
        this.ingredients = ingredients;
    }

    public Product(Long id, String uniqueId, String name, String category,
                   Double price, List<Ingredient> ingredients) {
        super(id, uniqueId, name, category, price);
        this.ingredients = ingredients;
    }

    public Product(String uniqueId, String name, String category,
                   Double price, List<Ingredient> ingredients) {
        super(uniqueId, name, category, price);
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public static class ProductBuilder {
        private String uniqueId;
        private String name;
        private String category;
        private Double price;
        private List<Ingredient> ingredients;

        public ProductBuilder setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
            return this;
        }

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

        public ProductBuilder setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Product build() {
            return new Product(this.uniqueId, this.name, this.category,
                    this.price, this.ingredients);
        }
    }

}
