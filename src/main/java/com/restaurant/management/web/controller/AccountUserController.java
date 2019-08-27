package com.restaurant.management.web.controller;

import com.restaurant.management.config.LogLogin;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.facade.AccountUserFacade;
import com.restaurant.management.service.facade.CompanyAccountUserFacade;
import com.restaurant.management.web.request.user.LoginRequest;
import com.restaurant.management.web.request.user.UserUpdateRequest;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import com.restaurant.management.web.response.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
public class AccountUserController {

    private AccountUserFacade accountUserFacade;
    private CompanyAccountUserFacade companyAccountUserFacade;

    @Autowired
    public AccountUserController(AccountUserFacade accountUserFacade,
                                 CompanyAccountUserFacade companyAccountUserFacade) {
        this.accountUserFacade = accountUserFacade;
        this.companyAccountUserFacade = companyAccountUserFacade;
    }

    @LogLogin
    @PostMapping(value = "/login", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        JwtAuthenticationResponse jwt = accountUserFacade.authenticateUser(loginRequest);

        UserResponse userResponse = companyAccountUserFacade.getUserDataFromJWT(jwt.getAccessToken());

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/userData", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    UserResponse getUserData(@CurrentUser UserPrincipal currentUser) {
        return companyAccountUserFacade.getUserData(currentUser);
    }

    @PutMapping(value = "/userData", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    UserResponse updateUserData(@CurrentUser UserPrincipal currentUser, @RequestBody UserUpdateRequest userUpdateRequest) {
        return companyAccountUserFacade.updateUserDetails(currentUser, userUpdateRequest);
    }

}
