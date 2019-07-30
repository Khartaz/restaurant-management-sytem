package com.restaurant.management.web.response;

import com.restaurant.management.domain.ecommerce.RoleName;

public final class RoleResponse {

    private RoleName name;

    public RoleResponse() {
    }

    public RoleResponse(RoleName name) {
        this.name = name;
    }

    public RoleName getName() {
        return name;
    }
}
