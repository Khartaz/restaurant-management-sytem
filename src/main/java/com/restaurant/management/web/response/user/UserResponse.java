package com.restaurant.management.web.response.user;

import com.restaurant.management.domain.layout.Settings;

public final class UserResponse {
    private UserDetailsResponse userDetails;
    private Settings settings;
    private String[] shortcuts;
    private String accessToken;

    public UserResponse() {
    }

    public UserResponse(UserDetailsResponse userDetails, Settings settings) {
        this.userDetails = userDetails;
        this.settings = settings;
    }

    public UserResponse(UserDetailsResponse userDetails) {
        this.userDetails = userDetails;
    }

    public UserResponse(UserDetailsResponse userDetails, Settings settings,
                        String[] shortcuts) {
        this.userDetails = userDetails;
        this.settings = settings;
        this.shortcuts = shortcuts;
    }

    public UserResponse(UserDetailsResponse userDetails,
                        Settings settings, String[] shortcuts, String accessToken) {
        this.userDetails = userDetails;
        this.settings = settings;
        this.shortcuts = shortcuts;
        this.accessToken = accessToken;
    }

    public UserDetailsResponse getUserDetails() {
        return userDetails;
    }

    public Settings getSettings() {
        return settings;
    }

    public String[] getShortcuts() {
        return shortcuts;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
