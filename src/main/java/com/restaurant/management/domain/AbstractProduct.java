package com.restaurant.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;

@MappedSuperclass
@JsonIgnoreProperties(allowGetters = true)
public abstract class AbstractProduct {

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
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;

    public AbstractProduct() {
    }

    public AbstractProduct(Long id, String uniqueId, String name,
                           String category, Double price, Calendar createdAt) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.createdAt = createdAt;
    }

    public AbstractProduct(String uniqueId, String name,
                           String category, Double price, Calendar createdAt) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.createdAt = createdAt;
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

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }
}
