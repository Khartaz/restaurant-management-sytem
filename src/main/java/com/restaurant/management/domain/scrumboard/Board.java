package com.restaurant.management.domain.scrumboard;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "uri")
    private String uri;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BoardSettings boardSettings;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BoardList> lists;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Card> cards;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Member> members;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Label> labels;

    public Board() {
    }

    public Board(Long id, String name, String uri, BoardSettings boardSettings,
                 List<BoardList> lists, List<Card> cards,
                 List<Member> members, List<Label> labels) {
        this.id = id;
        this.name = name;
        this.uri = uri;
        this.boardSettings = boardSettings;
        this.lists = lists;
        this.cards = cards;
        this.members = members;
        this.labels = labels;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public BoardSettings getBoardSettings() {
        return boardSettings;
    }

    public void setBoardSettings(BoardSettings boardSettings) {
        this.boardSettings = boardSettings;
    }

    public List<BoardList> getLists() {
        return lists;
    }

    public void setLists(List<BoardList> lists) {
        this.lists = lists;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}
