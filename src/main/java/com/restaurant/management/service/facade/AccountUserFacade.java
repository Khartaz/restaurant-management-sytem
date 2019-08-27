package com.restaurant.management.service.facade;

import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.web.request.user.LoginRequest;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class AccountUserFacade {

    private AccountUserService accountUserService;

    @Autowired
    public AccountUserFacade(AccountUserService AccountUserService) {
        this.accountUserService = AccountUserService;
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        return accountUserService.authenticateUser(loginRequest);
    }

}
