package com.restaurant.management.web.response.user;

public final class UserSummary {
    private Long id;
    private String username;
    private String name;

    public UserSummary() {
    }

    public UserSummary(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

}
