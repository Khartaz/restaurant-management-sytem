package com.restaurant.management.web.response;

import com.restaurant.management.domain.RoleName;

public class RoleResponse {

    private RoleName name;

    public RoleResponse(RoleName name) {
        this.name = name;
    }

    public RoleName getName() {
        return name;
    }
}
