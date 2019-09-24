package com.restaurant.management.mapper.ecommerce;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.Role;
import com.restaurant.management.domain.ecommerce.RoleName;
import com.restaurant.management.domain.ecommerce.dto.RoleDto;
import com.restaurant.management.web.response.RoleResponse;
import org.springframework.stereotype.Component;

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

    public static String mapRoleToString(RoleName roleName) {
        switch (roleName) {
            case ROLE_MANAGER:
                return "Manager";
            case ROLE_ADMIN:
                return "Admin";
            case ROLE_EMPLOYEE:
                return "Employee";
            case ROLE_USER:
                return "User";
        }
        return "RoleNotExists";
    }


    public static String roleToString(AccountUser accountUser) {
        return accountUser.getRoles().stream()
                .map(r -> RoleMapper.mapRoleToString(r.getName()))
                .collect(Collectors.joining());
    }


}
