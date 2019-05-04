package com.restaurant.management.domain.history;

import com.restaurant.management.domain.Product;
import org.hibernate.envers.RevisionType;

public final class ProductHistory {

    private Product product;
    private Number revision;
    private RevisionType revisionType;
    private Long createdAt;
    private Long updatedAt;
    private String createdBy;
    private String updatedBy;

    public ProductHistory(Product product, Number revision,
                          RevisionType revisionType,
                          Long createdAt, Long updatedAt,
                          String createdBy, String updatedBy) {
        this.product = product;
        this.revision = revision;
        this.revisionType = revisionType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }
}
