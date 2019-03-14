package com.restaurant.management.domain.archive;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products_archive")
public class ProductArchive  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private Double price;

    @Column(name = "createdAt")
    private Instant createdAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IngredientArchive> ingredients = new ArrayList<>();

    public ProductArchive() {
    }

    public ProductArchive(String uniqueId, String name, String category,
                          Double price, Instant createdAt, List<IngredientArchive> ingredients) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.createdAt = createdAt;
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<IngredientArchive> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientArchive> ingredients) {
        this.ingredients = ingredients;
    }
}
