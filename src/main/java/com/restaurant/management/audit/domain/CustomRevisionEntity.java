package com.restaurant.management.audit.domain;

import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class CustomRevisionEntity implements Serializable {

    private static final long serialVersionUID = 8530213963961662300L;

    @Id
    @RevisionNumber
    @GeneratedValue
    @Column(name = "revision_number")
    private Long revisionNumber;

    @RevisionTimestamp
    @Column(name = "revision_timestamp")
    private Long revisionTimestamp;

    public CustomRevisionEntity() {
    }

    public Long getRevisionNumber() {
        return this.revisionNumber;
    }

    @Transient
    public Date getRevisionDate() {
        return new Date(this.revisionTimestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomRevisionEntity that = (CustomRevisionEntity) o;
        return Objects.equals(revisionNumber, that.revisionNumber) &&
                Objects.equals(revisionTimestamp, that.revisionTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(revisionNumber, revisionTimestamp);
    }

    public String toString() {
        return "CustomRevisionEntity(revisionNumber = " + this.revisionNumber + ", revisionDate = " + DateFormat.getDateTimeInstance().format(this.getRevisionDate()) + ")";
    }
}
