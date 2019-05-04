package com.restaurant.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@MappedSuperclass
@JsonIgnoreProperties(allowGetters = true)
@Audited
public abstract class AbstractProduct extends AbstractAuditing {
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

    public AbstractProduct() {
    }

    public AbstractProduct(Long createdAt, Long updatedAt,
                           String createdBy, String updatedBy,
                           Long id, String uniqueId, String name,
                           String category, Double price) {
        super(createdAt, updatedAt, createdBy, updatedBy);
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public AbstractProduct(Long id, String uniqueId, String name,
                           String category, Double price) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public AbstractProduct(String uniqueId, String name,
                           String category, Double price) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Long getId() {
        return id;
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

}
