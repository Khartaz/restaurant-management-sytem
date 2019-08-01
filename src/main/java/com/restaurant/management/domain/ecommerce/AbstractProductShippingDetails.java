package com.restaurant.management.domain.ecommerce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@MappedSuperclass
@JsonIgnoreProperties(allowGetters = true)
@Audited
public abstract class AbstractProductShippingDetails extends AbstractAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "depth")
    private Double depth;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "extra_shipping_fee")
    private Double extraShippingFee;

    public AbstractProductShippingDetails() {
    }

    public AbstractProductShippingDetails(Double width, Double height, Double depth,
                                          Double weight, Double extraShippingFee) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;
        this.extraShippingFee = extraShippingFee;
    }

    public AbstractProductShippingDetails(Long createdAt, Long updatedAt, String createdByUserId,
                                          String updatedByUserId, Double width, Double height, Double depth, Double weight, Double extraShippingFee) {
        super(createdAt, updatedAt, createdByUserId, updatedByUserId);
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;
        this.extraShippingFee = extraShippingFee;
    }

    public Long getId() {
        return id;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getExtraShippingFee() {
        return extraShippingFee;
    }

    public void setExtraShippingFee(Double extraShippingFee) {
        this.extraShippingFee = extraShippingFee;
    }
}
