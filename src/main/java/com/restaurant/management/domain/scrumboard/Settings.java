package com.restaurant.management.domain.scrumboard;

import javax.persistence.*;

@Entity
@Table(name = "settings")
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "color")
    private String color;

    @Column(name = "is_subscribed")
    private Boolean isSubscribed;

    @Column(name = "card_cover_images")
    private Boolean cardCoverImages;

    public Settings() {
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
