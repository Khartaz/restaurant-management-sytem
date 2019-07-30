package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.Role;
import com.restaurant.management.domain.ecommerce.RoleName;
import com.restaurant.management.domain.ecommerce.dto.RoleDto;
import com.restaurant.management.web.response.RoleResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class RoleMapper {

    public Role mapToRole(final RoleDto roleDto) {
        return new Role(roleDto.getName());
    }

    public RoleDto mapToRoleDto(final Role role) {
        return new RoleDto(role.getName());
    }

    public RoleResponse mapToRoleResponse(final RoleDto roleDto) {
        return new RoleResponse(roleDto.getName());
    }

    public List<RoleDto> mapToRoleDtoList(final List<Role> roles) {
        return roles.stream()
                .map(this::mapToRoleDto)
                .collect(Collectors.toList());
    }

    public List<RoleResponse> mapToRoleResponseList(final List<RoleDto> roles) {
        return roles.stream()
                .map(this::mapToRoleResponse)
                .collect(Collectors.toList());
    }

    public static String mapRoleToString(RoleName roleName) {
        switch (roleName) {
            case ROLE_MANAGER:
                return "Manager";
            case ROLE_ADMIN:
                return "Admin";
            case ROLE_EMPLOYEE:
                return "Employee";
        }
        return "RoleNotExists";
    }

}
