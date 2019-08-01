package com.restaurant.management.domain.ecommerce;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product_ordered_shipping_details")
@Audited
public class ProductOrderedShippingDetails extends AbstractProductShippingDetails {

    public ProductOrderedShippingDetails() {
    }

    public ProductOrderedShippingDetails(Double width, Double height,
                                         Double depth, Double weight,
                                         Double extraShippingFee) {
        super(width, height, depth, weight, extraShippingFee);
    }

    public ProductOrderedShippingDetails(Long createdAt, Long updatedAt, String createdByUserId,
                                         String updatedByUserId, Long id,  Double width, Double height,
                                         Double depth, Double weight, Double extraShippingFee) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId, id, width, height, depth, weight, extraShippingFee);
    }
}
