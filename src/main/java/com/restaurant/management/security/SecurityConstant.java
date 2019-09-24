package com.restaurant.management.security;

import com.restaurant.management.SpringApplicationContext;

public final class SecurityConstant {
    public static final String BASE_URL = "https://shielded-brook-69228.herokuapp.com/api";
    public static final long EMAIL_VERIFICATION_EXPIRATION_TIME = 864000000; //10 days
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; //1hour
    public static final String AUTH_URL = "/api/auth/**";
    public static final String RESTAURANT_REGISTER = "/api/company/register";

    public static final String[] SWAGGER = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    public static final String[] REACT_FILES = {
            "/",
            "/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
    };

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
        return appProperties.getTokenSecret();
    }
}
