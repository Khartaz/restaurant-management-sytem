package com.restaurant.management.security;

import com.restaurant.management.SpringApplicationContext;

public class SecurityConstans {
    public static final String BASE_URL = "http://localhost:8080/api";
    public static final long EMAIL_VERIFICATION_EXPIRATION_TIME = 864000000; //10 days
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; //1hour
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String USER_ID = "userUniqueId";
    public static final String AUTH_URL = "/api/auth/**";
    public static final String CUSTOMER_URL = "/api/customers/**";
    public static final String ACCOUNT_URL = "/api/accounts/**";
    public static final String ADMIN_URL = "/api/admin/**";
    public static final String PRODUCT_URL = "/api/products/**";
    public static final String ORDER_URL = "/api/orders/**";
    public static final String CART_URL = "/api/carts/**";

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
