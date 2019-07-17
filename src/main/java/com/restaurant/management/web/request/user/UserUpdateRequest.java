package com.restaurant.management.web.request.user;

import com.restaurant.management.domain.layout.Settings;
import com.restaurant.management.domain.layout.Shortcut;

import java.util.List;

public final class UserUpdateRequest {
    private UserDetailsRequest userDetails;
    private Settings settings;
    private List<Shortcut> shortcuts;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(UserDetailsRequest userDetails,
                             Settings settings,
                             List<Shortcut> shortcuts) {
        this.userDetails = userDetails;
        this.settings = settings;
        this.shortcuts = shortcuts;
    }

    public UserDetailsRequest getUserDetails() {
        return userDetails;
    }

    public Settings getSettings() {
        return settings;
    }

    public List<Shortcut> getShortcuts() {
        return shortcuts;
    }
}
