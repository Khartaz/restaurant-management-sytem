package com.restaurant.management.web.controller;

import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.web.request.LoginRequest;
import com.restaurant.management.web.request.SignUpUserRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/account")
public class AccountUserController {

    private AccountUserService accountUserService;

    @Autowired
    public void setAccountUserService(AccountUserService accountUserService) {
        this.accountUserService = accountUserService;
    }

    @PostMapping(value = "/signin",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(accountUserService.authenticateUser(loginRequest));
    }

    @PostMapping(value = "/signup",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUserAccount(@Valid @RequestBody SignUpUserRequest signUpUserRequest) {
        accountUserService.registerManagerAccount(signUpUserRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(signUpUserRequest.getUsername()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, UserMessages.REGISTER_SUCCESS.getErrorMessage()));
    }

}
