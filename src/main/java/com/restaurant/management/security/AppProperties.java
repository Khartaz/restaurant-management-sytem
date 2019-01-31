package com.restaurant.management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

@Component
public class AppProperties {
    @Autowired
    private Environment environment;

    public String getTokenSecret() {
        return environment.getProperty("app.jwtSecret");
    }
}