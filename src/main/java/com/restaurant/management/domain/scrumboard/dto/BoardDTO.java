package com.restaurant.management.domain.scrumboard.dto;

import java.util.List;

public class BoardDTO {
    private Long id;
    private String name;
    private String uri;
    private BoardSettingsDTO settings;
    private List<BoardListDTO> lists;
    private List<CardDTO> cards;
    private List<MemberDTO> members;
    private List<LabelDTO> labels;

    public BoardDTO() {
    }

    public BoardDTO(Long id, String name, String uri,
                    BoardSettingsDTO settings, List<BoardListDTO> lists,
                    List<CardDTO> cards, List<MemberDTO> members,
                    List<LabelDTO> labels) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.settings = settings;
        this.lists = lists;
        this.cards = cards;
        this.members = members;
        this.labels = labels;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public BoardSettingsDTO getSettings() {
        return settings;
    }

    public void setSettings(BoardSettingsDTO settings) {
        this.settings = settings;
    }

    public List<BoardListDTO> getLists() {
        return lists;
    }

    public void setLists(List<BoardListDTO> lists) {
        this.lists = lists;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }

    public List<MemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<MemberDTO> members) {
        this.members = members;
    }

    public List<LabelDTO> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelDTO> labels) {
        this.labels = labels;
    }
}
