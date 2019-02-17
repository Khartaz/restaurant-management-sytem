package com.restaurant.management.mapper;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.dto.AccountUserDto;
import org.springframework.stereotype.Component;

@Component
public class AccountUserMapper {

    public AccountUser mapToAccountUser(final AccountUserDto accountUserDto) {
        return new AccountUser(
                accountUserDto.getId(),
                accountUserDto.getName(),
                accountUserDto.getLastname(),
                accountUserDto.getEmail(),
                accountUserDto.getUsername(),
                accountUserDto.getUserUniqueId(),
                accountUserDto.getPassword(),
                accountUserDto.getEmailVerificationToken(),
                accountUserDto.getActive(),
                accountUserDto.getRoles()
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
                accountUser.getPassword(),
                accountUser.getEmailVerificationToken(),
                accountUser.getActive(),
                accountUser.getRoles()
        );
    }
}
