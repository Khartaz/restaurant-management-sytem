package com.restaurant.management.web.response.user;

public final class UserSummary {
    private Long id;
    private String username;
    private String name;
    private String lastname;
    private String email;

    public UserSummary() {
    }

    public UserSummary(Long id, String username, String name,
                       String lastname, String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
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

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }
}
