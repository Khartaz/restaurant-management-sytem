package com.restaurant.management.service.ecommerce.facade;

import com.restaurant.management.domain.ecommerce.User;
import com.restaurant.management.domain.ecommerce.dto.UserDTO;
import com.restaurant.management.mapper.ecommerce.UserMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.UserService;
import com.restaurant.management.web.request.user.LoginRequest;
import com.restaurant.management.web.request.user.NewPasswordRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class UserFacade {

    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserFacade(UserService UserService,
                      UserMapper userMapper) {
        this.userService = UserService;
        this.userMapper = userMapper;
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest);
    }

    public UserDTO updateAccountInfo(@CurrentUser UserPrincipal currentUser, UserDTO userDTO) {
        User user = userService.updateAccountInfo(currentUser, userDTO);

        return userMapper.mapToAccountUserDTO(user);
    }

    public UserDTO getAccountInfo(@CurrentUser UserPrincipal currentUser) {
        User user = userService.getCompanyUserById(currentUser, currentUser.getId());

        return userMapper.mapToAccountUserDTO(user);
    }

    public ApiResponse newPasswordRequest(@CurrentUser UserPrincipal userPrincipal, NewPasswordRequest request) {
        return userService.newPasswordRequest(userPrincipal, request);
    }
}
