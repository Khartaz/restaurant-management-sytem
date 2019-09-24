package com.restaurant.management.domain.scrumboard.dto;

public class SettingsDTO {
    private Long id;
    private String color;
    private Boolean isSubscribed;
    private Boolean cardCoverImages;

    public SettingsDTO() {

    }

    public SettingsDTO(Long id, String color, Boolean isSubscribed, Boolean cardCoverImages) {
        this.id = id;
        this.color = color;
        this.isSubscribed = isSubscribed;
        this.cardCoverImages = cardCoverImages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
