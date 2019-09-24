package com.restaurant.management.domain.scrumboard.dto;

import java.util.List;

public class CheckListDTO {
    private Long id;
    private String name;
    private List<CheckItemDTO> checkItems;

    public CheckListDTO() {

    }

    public CheckListDTO(Long id, String name, List<CheckItemDTO> checkItems) {
        this.id = id;
        this.name = name;
        this.checkItems = checkItems;
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

    public List<CheckItemDTO> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<CheckItemDTO> checkItems) {
        this.checkItems = checkItems;
    }
}
