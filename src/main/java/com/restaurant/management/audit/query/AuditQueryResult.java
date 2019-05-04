package com.restaurant.management.audit.query;

import com.restaurant.management.audit.domain.CustomRevisionEntity;
import org.hibernate.envers.RevisionType;

public class AuditQueryResult<T> {

    private final T entity;
    private final CustomRevisionEntity revision;
    private final RevisionType revisionType;

    public AuditQueryResult(T entity, CustomRevisionEntity revision, RevisionType revisionType) {
        this.entity = entity;
        this.revision = revision;
        this.revisionType = revisionType;
    }

    public T getEntity() {
        return entity;
    }

    public CustomRevisionEntity getRevision() {
        return revision;
    }

    public RevisionType getRevisionType() {
        return revisionType;
    }
}
