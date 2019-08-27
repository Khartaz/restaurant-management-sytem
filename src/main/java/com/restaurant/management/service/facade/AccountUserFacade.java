package com.restaurant.management.service.facade;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.dto.AccountUserDto;
import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.web.request.user.LoginRequest;
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

    public AccountUserDto getUserById(Long id) {
        AccountUser accountUser = accountUserService.getUserById(id);

        return accountUserMapper.mapToAccountUserDto(accountUser);
    }

}
