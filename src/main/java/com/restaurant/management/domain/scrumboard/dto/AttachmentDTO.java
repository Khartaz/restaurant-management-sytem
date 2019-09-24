package com.restaurant.management.domain.scrumboard.dto;

public class AttachmentDTO {
    private Long id;
    private String name;
    private String src;
    private String time;
    private String type;

    public AttachmentDTO() {
    }

    public AttachmentDTO(Long id, String name, String src, String time, String type) {
        this.id = id;
        this.name = name;
        this.src = src;
        this.time = time;
        this.type = type;
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

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
