package com.restaurant.management.mapper;

import com.restaurant.management.domain.Role;
import com.restaurant.management.domain.dto.RoleDto;
import com.restaurant.management.web.response.RoleResponse;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role mapToRole(final RoleDto roleDto) {
        return new Role(roleDto.getName());
    }

    public RoleDto mapToRoleDto(final Role role) {
        return new RoleDto(role.getName());
    }

    public RoleResponse mapToRoleResponse(final RoleDto roleDto) {
        return new RoleResponse(roleDto.getName());
    }
}
