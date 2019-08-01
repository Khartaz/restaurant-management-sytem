package com.restaurant.management.domain.ecommerce;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product_inventory")
@Audited
public class ProductInventory extends AbstractProductInventory {

    public ProductInventory() {
    }

    public ProductInventory(String sku, Double quantity) {
        super(sku, quantity);
    }

    public ProductInventory(Long createdAt, Long updatedAt, String createdByUserId,
                            String updatedByUserId, Long id, String sku, Double quantity) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, sku, quantity);
    }

}
