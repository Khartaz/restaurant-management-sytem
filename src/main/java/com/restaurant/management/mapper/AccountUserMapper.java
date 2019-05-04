package com.restaurant.management.mapper;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.dto.AccountUserDto;
import com.restaurant.management.web.response.user.AccountUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("Duplicates")
public final class AccountUserMapper {

    private RoleMapper roleMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public AccountUser mapToAccountUser(final AccountUserDto accountUserDto) {
        return new AccountUser(
                accountUserDto.getId(),
                accountUserDto.getName(),
                accountUserDto.getLastname(),
                accountUserDto.getEmail(),
                accountUserDto.getUsername(),
                accountUserDto.getEmailVerificationToken(),
                accountUserDto.isActive(),
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
                accountUser.getEmailVerificationToken(),
                accountUser.isActive(),
                accountUser.getRoles().stream()
                        .map(v -> roleMapper.mapToRoleDto(v))
                        .collect(Collectors.toSet())
        );
    }

    public AccountUserResponse mapToAccountUserResponse(final AccountUserDto accountUserDto) {
        return new AccountUserResponse(
                accountUserDto.getId(),
                accountUserDto.getName(),
                accountUserDto.getLastname(),
                accountUserDto.getEmail(),
                accountUserDto.getUsername(),
                accountUserDto.getEmailVerificationToken(),
                accountUserDto.isActive(),
                accountUserDto.getRoles().stream()
                        .map(r -> roleMapper.mapToRoleResponse(r))
                        .collect(Collectors.toSet())
        );
    }

    public List<AccountUser> mapToAccountUserList(final List<AccountUserDto> accountUsersDto) {

        return accountUsersDto.stream()
                .map(u -> new AccountUser(
                        u.getId(),
                        u.getName(),
                        u.getLastname(),
                        u.getEmail(),
                        u.getUsername(),
                        u.getEmailVerificationToken(),
                        u.isActive(),
                        u.getRoles().stream()
                                .map(r -> roleMapper.mapToRole(r))
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
    }

    public List<AccountUserDto> mapToAccountUserListDto(final List<AccountUser> accountUsers) {
        return accountUsers.stream()
                .map(u -> new AccountUserDto(
                        u.getId(),
                        u.getName(),
                        u.getLastname(),
                        u.getEmail(),
                        u.getUsername(),
                        u.getEmailVerificationToken(),
                        u.isActive(),
                        u.getRoles().stream()
                                .map(r -> roleMapper.mapToRoleDto(r))
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
    }

    public Page<AccountUserDto> mapToAccountUserDtoPage(final Page<AccountUser> accountUsers) {
        return accountUsers.map(this::mapToAccountUserDto);
    }


    public List<AccountUserResponse> mapToAccountUserListResponse(final List<AccountUserDto> accountUsersDto) {
        return accountUsersDto.stream()
                .map(u -> new AccountUserResponse(
                        u.getId(),
                        u.getName(),
                        u.getLastname(),
                        u.getEmail(),
                        u.getUsername(),
                        u.getEmailVerificationToken(),
                        u.isActive(),
                        u.getRoles().stream()
                        .map(r -> roleMapper.mapToRoleResponse(r))
                                .collect(Collectors.toSet())
                )).collect(Collectors.toList());
    }

    public Page<AccountUserResponse> mapToAccountUserResponsePage(final Page<AccountUserDto> accountUsers) {
        return accountUsers.map(this::mapToAccountUserResponse);
    }

}
