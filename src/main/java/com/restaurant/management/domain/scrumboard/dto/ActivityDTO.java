package com.restaurant.management.domain.scrumboard.dto;

public class ActivityDTO {
    private Long id;
    private String type;
    private String memberId;
    private String message;
    private String time;

    public ActivityDTO() {
    }

    public ActivityDTO(Long id, String type, String memberId, String message, String time) {
        this.id = id;
        this.type = type;
        this.memberId = memberId;
        this.message = message;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
