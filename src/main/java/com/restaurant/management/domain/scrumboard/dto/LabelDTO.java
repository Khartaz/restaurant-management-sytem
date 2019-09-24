package com.restaurant.management.domain.scrumboard.dto;

public class LabelDTO {
    private Long id;
    private String name;
    private String className;

    public LabelDTO() {
    }

    public LabelDTO(Long id, String name, String className) {
        this.id = id;
        this.name = name;
        this.className = className;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
