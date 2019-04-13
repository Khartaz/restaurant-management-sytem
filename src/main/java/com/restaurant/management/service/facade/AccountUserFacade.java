package com.restaurant.management.service.facade;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.dto.AccountUserDto;
import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.web.request.LoginRequest;
import com.restaurant.management.web.request.SignUpUserRequest;
import com.restaurant.management.web.request.UpdateAccountNameOrLastname;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AccountUserFacade {

    private AccountUserService accountUserService;
    private AccountUserMapper accountUserMapper;

    @Autowired
    public AccountUserFacade(AccountUserService accountUserService, AccountUserMapper accountUserMapper) {
        this.accountUserService = accountUserService;
        this.accountUserMapper = accountUserMapper;
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        return accountUserService.authenticateUser(loginRequest);
    }

    public AccountUserDto registerManagerAccount(SignUpUserRequest request) {
        AccountUser accountUser = accountUserService.registerManagerAccount(request);

        return accountUserMapper.mapToAccountUserDto(accountUser);
    }

    public AccountUserDto updateAccountNameOrLastname(UpdateAccountNameOrLastname request) {
        AccountUser accountUser = accountUserService.updateAccountNameOrLastname(request);

        return accountUserMapper.mapToAccountUserDto(accountUser);
    }

    public ApiResponse deleteUserById(Long id) {
        return accountUserService.deleteUserById(id);
    }

    public Page<AccountUserDto> getAllAccountUsers(Pageable pageable) {
        Page<AccountUser> accountUsers = accountUserService.getAllAccountUsers(pageable);

        return accountUserMapper.mapToAccountUserDtoPage(accountUsers);
    }

    public AccountUserDto getUserByUserUniqueId(String userUniqueId) {
        AccountUser accountUser = accountUserService.getUserByUserUniqueId(userUniqueId);

        return accountUserMapper.mapToAccountUserDto(accountUser);
    }

    public AccountUserDto registerAdminAccount(SignUpUserRequest request) {
        AccountUser accountUser = accountUserService.registerAdminAccount(request);

        return accountUserMapper.mapToAccountUserDto(accountUser);
    }
}
