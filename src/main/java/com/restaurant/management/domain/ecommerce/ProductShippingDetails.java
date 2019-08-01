package com.restaurant.management.domain.ecommerce;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product_shipping_details")
@Audited
public class ProductShippingDetails extends AbstractProductShippingDetails {

    public ProductShippingDetails() {
    }

    public ProductShippingDetails(Double width, Double height,
                                  Double depth, Double weight, Double extraShippingFee) {
        super(width, height, depth, weight, extraShippingFee);
    }

    public ProductShippingDetails(Long createdAt, Long updatedAt, String createdByUserId,
                                  String updatedByUserId, Long id,  Double width, Double height,
                                  Double depth, Double weight, Double extraShippingFee) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId,
                id, width, height, depth, weight, extraShippingFee);
    }

}
