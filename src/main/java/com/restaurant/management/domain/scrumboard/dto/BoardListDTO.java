package com.restaurant.management.domain.scrumboard.dto;

public class BoardListDTO {
    private Long id;
    private String name;
    private String[] cardsIds;

    public BoardListDTO() {
    }

    public BoardListDTO(Long id, String name, String[] cardsIds) {
        this.id = id;
        this.name = name;
        this.cardsIds = cardsIds;
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

    public String[] getCardsIds() {
        return cardsIds;
    }

    public void setCardsIds(String[] cardsIds) {
        this.cardsIds = cardsIds;
    }
}
