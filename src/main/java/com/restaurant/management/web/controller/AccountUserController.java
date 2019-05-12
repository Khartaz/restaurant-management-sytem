package com.restaurant.management.web.controller;

import com.restaurant.management.config.LogLogin;
import com.restaurant.management.domain.Role;
import com.restaurant.management.domain.dto.AccountUserDto;
import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.facade.AccountUserFacade;
import com.restaurant.management.web.request.LoginRequest;
import com.restaurant.management.web.request.SignUpUserRequest;
import com.restaurant.management.web.request.UpdateAccountNameOrLastname;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.user.AccountUserResponse;
import com.restaurant.management.web.response.user.UserSummary;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
public class AccountUserController {

    private AccountUserFacade accountUserFacade;
    private AccountUserMapper accountUserMapper;

    @Autowired
    public AccountUserController(AccountUserFacade accountUserFacade,
                                 AccountUserMapper accountUserMapper) {
        this.accountUserFacade = accountUserFacade;
        this.accountUserMapper = accountUserMapper;
    }

    @GetMapping(value = "/me")
    public @ResponseBody
    UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return accountUserFacade.getUserSummary(currentUser);
    }

    @LogLogin
    @PostMapping(value = "/signin", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(accountUserFacade.authenticateUser(loginRequest));
    }

    @PostMapping(value = "/signup", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<AccountUserResponse> registerManagerAccount(@Valid @RequestBody SignUpUserRequest signUpUserRequest) {
        AccountUserDto accountUserDto = accountUserFacade.registerManagerAccount(signUpUserRequest);

        AccountUserResponse userResponse = accountUserMapper.mapToAccountUserResponse(accountUserDto);

        Link link = linkTo(AccountUserController.class).slash(userResponse.getId()).withSelfRel();
        return new Resource<>(userResponse, link);
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<AccountUserResponse> updateAccountNameOrLastname(@CurrentUser UserPrincipal currentUser,
                                                              @Valid @RequestBody UpdateAccountNameOrLastname request) {
        AccountUserDto accountUserDto = accountUserFacade.updateAccountNameOrLastname(currentUser, request);

        AccountUserResponse response = accountUserMapper.mapToAccountUserResponse(accountUserDto);

        Link link = linkTo(AccountUserController.class).slash(response.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @GetMapping(value = "/checkEmailAvailability", produces = APPLICATION_JSON_VALUE)
    public ApiResponse checkEmailAvailability(@RequestParam String email) {
        return accountUserFacade.checkEmailAvailability(email);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<AccountUserResponse>> showRestaurantUsersPageable(@CurrentUser UserPrincipal currentUser,
                                                                                    Pageable pageable,
                                                                                    PagedResourcesAssembler assembler) {
        Page<AccountUserDto> accountUsersDto = accountUserFacade.getRestaurantUsers(currentUser, pageable);

        Page<AccountUserResponse> responsePage = accountUserMapper.mapToAccountUserResponsePage(accountUsersDto);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<AccountUserResponse> showUserDetails(@CurrentUser UserPrincipal currentUser,
                                                  @PathVariable Long id) {
        AccountUserDto accountUserDto = accountUserFacade.getRestaurantUserById(currentUser, id);

        AccountUserResponse accountUserResponse = accountUserMapper.mapToAccountUserResponse(accountUserDto);

        Link link = linkTo(AccountUserController.class).slash(accountUserResponse.getId()).withSelfRel();

        return new Resource<>(accountUserResponse, link);
    }

}
