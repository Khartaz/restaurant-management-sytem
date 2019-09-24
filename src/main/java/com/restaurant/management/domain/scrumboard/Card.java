package com.restaurant.management.domain.scrumboard;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private String dueDate;

    @Column(name = "id_attachment_cover")
    private String idAttachmentCover;

    @Column(name = "members_ids")
    private String membersIds;

    @Column(name = "labels_ids")
    private String labelsIds;

    @Column(name = "is_subscribed")
    private Boolean isSubscribed;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CheckList> checkLists;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Activity> activities;

    public Card() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getIdAttachmentCover() {
        return idAttachmentCover;
    }

    public void setIdAttachmentCover(String idAttachmentCover) {
        this.idAttachmentCover = idAttachmentCover;
    }

    public String getMembersIds() {
        return membersIds;
    }

    public void setMembersIds(String membersIds) {
        this.membersIds = membersIds;
    }

    public String getLabelsIds() {
        return labelsIds;
    }

    public void setLabelsIds(String labelsIds) {
        this.labelsIds = labelsIds;
    }

    public Boolean getSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<CheckList> getCheckLists() {
        return checkLists;
    }

    public void setCheckLists(List<CheckList> checkLists) {
        this.checkLists = checkLists;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
