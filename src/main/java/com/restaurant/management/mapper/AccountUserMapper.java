package com.restaurant.management.mapper;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.dto.AccountUserDto;
import com.restaurant.management.web.response.AccountUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AccountUserMapper {

    private RoleMapper roleMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public AccountUser mapToAccountUser(final AccountUserDto accountUserDto) {
        return new AccountUser(
                accountUserDto.getName(),
                accountUserDto.getLastname(),
                accountUserDto.getEmail(),
                accountUserDto.getUsername(),
                accountUserDto.getUserUniqueId(),
                accountUserDto.getActive(),
                accountUserDto.getRoles().stream()
                        .map(v -> roleMapper.mapToRole(v))
                        .collect(Collectors.toSet())
        );
    }

    public AccountUserDto mapToAccountUserDto(final AccountUser accountUser) {
        return new AccountUserDto(
                accountUser.getId(),
                accountUser.getName(),
                accountUser.getLastname(),
                accountUser.getEmail(),
                accountUser.getUsername(),
                accountUser.getUserUniqueId(),
                accountUser.getActive(),
                accountUser.getRoles().stream()
                        .map(v -> roleMapper.mapToRoleDto(v))
                        .collect(Collectors.toSet())
        );
    }

    public AccountUserResponse mapToAccountUserResponse(final AccountUserDto accountUserDto) {
        return new AccountUserResponse(
                accountUserDto.getName(),
                accountUserDto.getLastname(),
                accountUserDto.getEmail(),
                accountUserDto.getUsername(),
                accountUserDto.getUserUniqueId(),
                accountUserDto.getActive(),
                accountUserDto.getRoles()
        );
    }
}
