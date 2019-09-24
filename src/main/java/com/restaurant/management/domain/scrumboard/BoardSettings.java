package com.restaurant.management.domain.scrumboard;

import javax.persistence.*;

@Entity
@Table(name = "board_settings")
public class BoardSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "color")
    private String color;

    @Column(name = "is_subscribed")
    private Boolean isSubscribed;

    @Column(name = "card_cover_images")
    private Boolean cardCoverImages;

    public BoardSettings() {
    }

    public BoardSettings(String color, Boolean isSubscribed,
                         Boolean cardCoverImages) {
        this.color = color;
        this.isSubscribed = isSubscribed;
        this.cardCoverImages = cardCoverImages;
    }

    public BoardSettings(Long id, String color, Boolean isSubscribed,
                         Boolean cardCoverImages) {
        this.id = id;
        this.color = color;
        this.isSubscribed = isSubscribed;
        this.cardCoverImages = cardCoverImages;
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }

    public Boolean getCardCoverImages() {
        return cardCoverImages;
    }

    public void setCardCoverImages(Boolean cardCoverImages) {
        this.cardCoverImages = cardCoverImages;
    }
}
