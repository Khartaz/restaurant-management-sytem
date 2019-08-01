package com.restaurant.management.domain.ecommerce;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product_inventory")
@Audited
public class ProductInventory extends AbstractProductInventory {

    public ProductInventory(String sku, Double quantity) {
        super(sku, quantity);
    }

    public ProductInventory(Long createdAt, Long updatedAt, String createdByUserId,
                            String updatedByUserId, String sku, Double quantity) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, sku, quantity);
    }

}
