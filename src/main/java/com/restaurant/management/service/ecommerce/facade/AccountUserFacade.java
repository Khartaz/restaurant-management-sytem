package com.restaurant.management.service.ecommerce.facade;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.dto.AccountUserDTO;
import com.restaurant.management.mapper.ecommerce.AccountUserMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.AccountUserService;
import com.restaurant.management.web.request.user.LoginRequest;
import com.restaurant.management.web.request.user.NewPasswordRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    public AccountUserDTO updateAccountInfo(@CurrentUser UserPrincipal currentUser, AccountUserDTO accountUserDTO) {
        AccountUser accountUser = accountUserService.updateAccountInfo(currentUser, accountUserDTO);

        return accountUserMapper.mapToAccountUserDTO(accountUser);
    }

    public AccountUserDTO getAccountInfo(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = accountUserService.getCompanyUserById(currentUser, currentUser.getId());

        return accountUserMapper.mapToAccountUserDTO(accountUser);
    }

    public ApiResponse newPasswordRequest(@CurrentUser UserPrincipal userPrincipal, NewPasswordRequest request) {
        return accountUserService.newPasswordRequest(userPrincipal, request);
    }
}
