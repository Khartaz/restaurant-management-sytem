package com.restaurant.management.domain.ecommerce;

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

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "tag")
    private String tag;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public AbstractProduct() {
    }

    public AbstractProduct(Long createdAt, Long updatedAt,
                           String createdByUserId, String updatedByUserId,
                           Long id, String name,
                           String category, Double price) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId);
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public AbstractProduct(Long id, String name,
                           String category, Double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public AbstractProduct(String name, String category, Double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Long getId() {
        return id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
