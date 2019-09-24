package com.restaurant.management.domain.scrumboard.dto;

public class CheckItemDTO {
    private Long id;
    private String name;
    private Boolean isChecked;

    public CheckItemDTO() {
    }

    public CheckItemDTO(Long id, String name, Boolean isChecked) {
        this.id = id;
        this.name = name;
        this.isChecked = isChecked;
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

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
