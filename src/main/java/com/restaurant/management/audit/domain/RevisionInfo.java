package com.restaurant.management.audit.domain;

import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.text.DateFormat;

@Entity
@RevisionEntity
@Table(name = "rev_info")
public class RevisionInfo extends CustomRevisionEntity {

    public RevisionInfo() {
    }

    public String toString() {
        return "RevisionInfo(revisionNumber = " + getRevisionNumber() + ", revisionDate = " + DateFormat.getDateTimeInstance().format(this.getRevisionDate()) + ")";
    }
}
