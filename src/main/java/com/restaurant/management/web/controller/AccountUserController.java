package com.restaurant.management.web.controller;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.dto.AccountUserDto;
import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.web.request.LoginRequest;
import com.restaurant.management.web.request.SignUpUserRequest;
import com.restaurant.management.web.response.AccountUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AccountUserController {

    private AccountUserService accountUserService;
    private AccountUserMapper accountUserMapper;

    @Autowired
    public AccountUserController(AccountUserService accountUserService, AccountUserMapper accountUserMapper) {
        this.accountUserService = accountUserService;
        this.accountUserMapper = accountUserMapper;
    }

    @PostMapping(value = "/accounts/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(accountUserService.authenticateUser(loginRequest));
    }


    @PostMapping(value = "/accounts/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<AccountUserResponse> registerUserAccount(@Valid @RequestBody SignUpUserRequest signUpUserRequest) {
        AccountUserDto accountUserDto = accountUserService.registerManagerAccount(signUpUserRequest);

        AccountUserResponse userResponse = accountUserMapper.mapToAccountUserResponse(accountUserDto);

        Link link = linkTo(AccountUserController.class).slash(userResponse.getUserUniqueId()).withSelfRel();
        return new Resource<>(userResponse, link);
    }

    @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Resources<AccountUser> showAllUsers() {
        List<AccountUser> accountUsers = accountUserService.getAllAccountUsers();
        Link link = linkTo(AccountUserController.class).withSelfRel();

        return new Resources<>(accountUsers, link);
    }

}
