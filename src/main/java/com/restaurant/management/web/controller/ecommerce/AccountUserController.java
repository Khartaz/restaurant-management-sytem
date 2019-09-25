package com.restaurant.management.web.controller.ecommerce;

import com.restaurant.management.config.LogLogin;
import com.restaurant.management.domain.ecommerce.dto.UserDTO;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.facade.UserFacade;
import com.restaurant.management.service.ecommerce.facade.CompanyAccountUserFacade;
import com.restaurant.management.web.request.user.LoginRequest;
import com.restaurant.management.web.request.user.NewPasswordRequest;
import com.restaurant.management.web.request.user.UserUpdateRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import com.restaurant.management.web.response.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
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

    private UserFacade userFacade;
    private CompanyAccountUserFacade companyAccountUserFacade;

    @Autowired
    public AccountUserController(UserFacade userFacade,
                                 CompanyAccountUserFacade companyAccountUserFacade) {
        this.userFacade = userFacade;
        this.companyAccountUserFacade = companyAccountUserFacade;
    }

    @LogLogin
    @PostMapping(value = "/login", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        JwtAuthenticationResponse jwt = userFacade.authenticateUser(loginRequest);

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
    UserResponse updateAccountSettings(@CurrentUser UserPrincipal currentUser, @RequestBody UserUpdateRequest userUpdateRequest) {
        return companyAccountUserFacade.updateAccountSettings(currentUser, userUpdateRequest);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<UserDTO> updateAccountInfo(@CurrentUser UserPrincipal currentUser,
                                        @Valid @RequestBody UserDTO userDTO) {

        UserDTO accountUser = userFacade.updateAccountInfo(currentUser, userDTO);

        Link link = linkTo(AccountUserController.class).slash(accountUser.getId()).withSelfRel();

        return new Resource<>(accountUser, link);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<UserDTO> getAccountInfo(@CurrentUser UserPrincipal currentUser) {
        UserDTO userDTO = userFacade.getAccountInfo(currentUser);

        Link link = linkTo(AccountUserController.class).slash(userDTO.getId()).withSelfRel();

        return new Resource<>(userDTO, link);
    }

    @PutMapping(value = "/reset-password", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ApiResponse newPasswordRequest(@CurrentUser UserPrincipal userPrincipal, @RequestBody NewPasswordRequest request) {
        return userFacade.newPasswordRequest(userPrincipal, request);
    }

}
