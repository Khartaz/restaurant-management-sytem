package com.restaurant.management.domain.scrumboard;

import javax.persistence.*;

@Entity
@Table(name = "board_lists")
public class BoardList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cards_ids")
    private String cardsIds;

    public BoardList() {
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

    public String getCardsIds() {
        return cardsIds;
    }

    public void setCardsIds(String cardsIds) {
        this.cardsIds = cardsIds;
    }

}
