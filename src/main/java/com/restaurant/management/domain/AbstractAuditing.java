package com.restaurant.management.domain;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Audited
public abstract class AbstractAuditing {

    @CreatedDate
    @Column(name = "createdAt", updatable = false, nullable = false)
    private Long createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    private Long updatedAt;

    @CreatedBy
    @Column(name = "created_by_username")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by_username")
    private String updatedBy;

    public AbstractAuditing() {
    }

    public AbstractAuditing(Long createdAt, Long updatedAt,
                            String createdBy, String updatedBy) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}