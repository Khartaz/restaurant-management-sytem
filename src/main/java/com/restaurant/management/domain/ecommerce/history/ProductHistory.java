package com.restaurant.management.domain.ecommerce.history;

import com.restaurant.management.domain.ecommerce.Product;
import org.hibernate.envers.RevisionType;

public final class ProductHistory {

    private Product product;
    private Number revision;
    private RevisionType revisionType;
    private Long createdAt;
    private Long updatedAt;
    private String createdByUserId;
    private String updatedByUserId;

    public ProductHistory(Product product, Number revision,
                          RevisionType revisionType,
                          Long createdAt, Long updatedAt,
                          String createdByUserId, String updatedByUserId) {
        this.product = product;
        this.revision = revision;
        this.revisionType = revisionType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdByUserId = createdByUserId;
        this.updatedByUserId = updatedByUserId;
    }

    public Product getProduct() {
        return product;
    }

    public Number getRevision() {
        return revision;
    }

    public RevisionType getRevisionType() {
        return revisionType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public String getUpdatedByUserId() {
        return updatedByUserId;
    }
}
