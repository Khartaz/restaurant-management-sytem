package com.restaurant.management.domain.scrumboard.dto;

import java.util.List;

public class CardDTO {
    private Long id;
    private String name;
    private String description;
    private String dueDate;
    private String idAttachmentCover;
    private String[] membersIds;
    private String[] labelsIds;
    private Boolean isSubscribed;
    private List<AttachmentDTO> attachments;
    private List<CheckListDTO> checkLists;
    private List<ActivityDTO> activities;

    public CardDTO() {
    }

    public CardDTO(Long id, String name, String description,
                   String dueDate, String idAttachmentCover,
                   String[] membersIds, String[] labelsIds,
                   Boolean isSubscribed, List<AttachmentDTO> attachments,
                   List<CheckListDTO> checkLists, List<ActivityDTO> activities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.idAttachmentCover = idAttachmentCover;
        this.membersIds = membersIds;
        this.labelsIds = labelsIds;
        this.isSubscribed = isSubscribed;
        this.attachments = attachments;
        this.checkLists = checkLists;
        this.activities = activities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String[] getMembersIds() {
        return membersIds;
    }

    public void setMembersIds(String[] membersIds) {
        this.membersIds = membersIds;
    }

    public String[] getLabelsIds() {
        return labelsIds;
    }

    public void setLabelsIds(String[] labelsIds) {
        this.labelsIds = labelsIds;
    }

    public Boolean getSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }

    public List<AttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentDTO> attachments) {
        this.attachments = attachments;
    }

    public List<CheckListDTO> getCheckLists() {
        return checkLists;
    }

    public void setCheckLists(List<CheckListDTO> checkLists) {
        this.checkLists = checkLists;
    }

    public List<ActivityDTO> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityDTO> activities) {
        this.activities = activities;
    }
}

