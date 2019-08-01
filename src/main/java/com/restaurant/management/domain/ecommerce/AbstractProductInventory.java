package com.restaurant.management.domain.ecommerce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@MappedSuperclass
@JsonIgnoreProperties(allowGetters = true)
@Audited
public abstract class AbstractProductInventory extends AbstractAuditing{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sku")
    private String sku;

    @Column(name = "quantity")
    private Double quantity;

    public AbstractProductInventory() {
    }

    public AbstractProductInventory(String sku, Double quantity) {
        this.sku = sku;
        this.quantity = quantity;
    }

    public AbstractProductInventory(Long createdAt, Long updatedAt, String createdByUserId,
                                    String updatedByUserId, Long id, String sku, Double quantity) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId);
        this.id = id;
        this.sku = sku;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
