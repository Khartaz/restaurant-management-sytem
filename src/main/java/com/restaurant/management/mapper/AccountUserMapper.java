package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.dto.AccountUserDto;
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

    public AccountUserDto mapToAccountUserDto(final AccountUser accountUser) {
        return new AccountUserDto(
                accountUser.getCreatedAt(),
                accountUser.getUpdatedAt(),
                accountUser.getCreatedByUserId(),
                accountUser.getUpdatedByUserId(),
                accountUser.getId(),
                accountUser.getName(),
                accountUser.getLastName(),
                accountUser.getEmail(),
                accountUser.getPhone(),
                accountUser.getEmailVerificationToken(),
                accountUser.isActive(),
                accountUser.getRoles().stream()
                        .map(v -> roleMapper.mapToRoleDto(v))
                        .collect(Collectors.toSet())
        );
    }

    public AccountUserResponse mapToAccountUserResponse(final AccountUserDto accountUserDto) {
        return new AccountUserResponse(
                accountUserDto.getCreatedAt(),
                accountUserDto.getUpdatedAt(),
                accountUserDto.getCreatedByUserId(),
                accountUserDto.getUpdatedByUserId(),
                accountUserDto.getId(),
                accountUserDto.getName(),
                accountUserDto.getLastName(),
                accountUserDto.getEmail(),
                accountUserDto.getPhone(),
                accountUserDto.getEmailVerificationToken(),
                accountUserDto.isActive(),
                accountUserDto.getRoles().stream()
                        .map(r -> roleMapper.mapToRoleResponse(r))
                        .collect(Collectors.toSet())
        );
    }

    public List<AccountUserDto> mapToAccountUserListDto(final List<AccountUser> accountUsers) {
        return accountUsers.stream()
                .map(this::mapToAccountUserDto)
                .collect(Collectors.toList());
    }

    public Page<AccountUserDto> mapToAccountUserDtoPage(final Page<AccountUser> accountUsers) {
        return accountUsers.map(this::mapToAccountUserDto);
    }


    public List<AccountUserResponse> mapToAccountUserListResponse(final List<AccountUserDto> accountUsersDto) {
        return accountUsersDto.stream()
                .map(this::mapToAccountUserResponse)
                .collect(Collectors.toList());
    }

    public Page<AccountUserResponse> mapToAccountUserResponsePage(final Page<AccountUserDto> accountUsers) {
        return accountUsers.map(this::mapToAccountUserResponse);
    }

}
