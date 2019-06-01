package com.restaurant.management.service.facade;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.RoleName;
import com.restaurant.management.domain.dto.AccountUserDto;
import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.mapper.RoleMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.web.request.account.LoginRequest;
import com.restaurant.management.web.request.account.SignUpUserRequest;
import com.restaurant.management.web.request.account.UpdateAccountInfo;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import com.restaurant.management.web.response.user.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public final class AccountUserFacade {

    private AccountUserService accountUserService;
    private AccountUserMapper accountUserMapper;

    @Autowired
    public AccountUserFacade(AccountUserService AccountUserService,
                             AccountUserMapper accountUserMapper) {
        this.accountUserService = AccountUserService;
        this.accountUserMapper = accountUserMapper;
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        return accountUserService.authenticateUser(loginRequest);
    }

    public AccountUserDto registerManagerAccount(SignUpUserRequest request) {
        AccountUser accountUser = accountUserService.registerManagerAccount(request);

        return accountUserMapper.mapToAccountUserDto(accountUser);
    }

    public AccountUserDto updateAccountInfo(@CurrentUser UserPrincipal currentUser,
                                            UpdateAccountInfo request) {
        AccountUser accountUser = accountUserService.updateAccountInfo(currentUser, request);

        return accountUserMapper.mapToAccountUserDto(accountUser);
    }

    public ApiResponse deleteUserById(Long id) {
        return accountUserService.deleteUserById(id);
    }

    public Page<AccountUserDto> getAllAccountUsers(Pageable pageable) {
        Page<AccountUser> accountUsers = accountUserService.getAllAccountUsers(pageable);

        return accountUserMapper.mapToAccountUserDtoPage(accountUsers);
    }

    public Page<AccountUserDto> getRestaurantUsers(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<AccountUser> accountUsers = accountUserService.getRestaurantUsers(currentUser, pageable);

        return accountUserMapper.mapToAccountUserDtoPage(accountUsers);
    }

    public AccountUserDto getUserById(Long id) {
        AccountUser accountUser = accountUserService.getUserById(id);

        return accountUserMapper.mapToAccountUserDto(accountUser);
    }

    public AccountUserDto getRestaurantUserById(@CurrentUser UserPrincipal currentUser,  Long id) {
        AccountUser accountUser = accountUserService.getRestaurantUserById(currentUser, id);

        return accountUserMapper.mapToAccountUserDto(accountUser);
    }

    public AccountUserDto registerAdminAccount(SignUpUserRequest request) {
        AccountUser accountUser = accountUserService.registerAdminAccount(request);

        return accountUserMapper.mapToAccountUserDto(accountUser);
    }

    public ApiResponse checkEmailAvailability(String email) {
        return accountUserService.checkEmailAvailability(email);
    }

    public boolean activateAccount(String token) {
        accountUserService.verifyEmailToken(token);
        return true;
    }

    public RoleName[] getRoles() {
        RoleName[] roles = accountUserService.getRoles();

        return roles;
    }

}
