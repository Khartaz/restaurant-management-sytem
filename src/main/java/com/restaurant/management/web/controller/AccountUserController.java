package com.restaurant.management.web.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
public class AccountUserController {

    private AccountUserService accountUserService;
    private AccountUserMapper accountUserMapper;

    @Autowired
    public AccountUserController(AccountUserService accountUserService, AccountUserMapper accountUserMapper) {
        this.accountUserService = accountUserService;
        this.accountUserMapper = accountUserMapper;
    }

    @PostMapping(value = "/signin", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(accountUserService.authenticateUser(loginRequest));
    }


    @PostMapping(value = "/signup", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<AccountUserResponse> registerUserAccount(@Valid @RequestBody SignUpUserRequest signUpUserRequest) {
        AccountUserDto accountUserDto = accountUserService.registerManagerAccount(signUpUserRequest);

        AccountUserResponse userResponse = accountUserMapper.mapToAccountUserResponse(accountUserDto);

        Link link = linkTo(AccountUserController.class).slash(userResponse.getUserUniqueId()).withSelfRel();
        return new Resource<>(userResponse, link);
    }

    @DeleteMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountUserService.deleteUserById(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resources<AccountUserResponse> showAllUsers() {
        List<AccountUserDto> accountUsersDto = accountUserService.getAllAccountUsers();

        List<AccountUserResponse> accountUsersResponse = accountUserMapper.mapToAccountUserListResponse(accountUsersDto);
        Link link = linkTo(AccountUserController.class).withSelfRel();

        return new Resources<>(accountUsersResponse, link);
    }

}
