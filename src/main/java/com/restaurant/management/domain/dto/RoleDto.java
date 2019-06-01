package com.restaurant.management.domain.dto;

import com.restaurant.management.domain.RoleName;

public final class RoleDto {
    private RoleName name;

    public RoleDto(RoleName name) {
        this.name = name;
    }

    public RoleName getName() {
        return name;
    }

}
