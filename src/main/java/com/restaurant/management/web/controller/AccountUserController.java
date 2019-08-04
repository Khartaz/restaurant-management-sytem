package com.restaurant.management.web.controller;

import com.restaurant.management.config.LogLogin;
import com.restaurant.management.domain.ecommerce.dto.AccountUserDto;
import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.LayoutSettingsService;
import com.restaurant.management.service.LayoutShortcutService;
import com.restaurant.management.service.facade.AccountUserFacade;
import com.restaurant.management.service.facade.CompanyAccountUserFacade;
import com.restaurant.management.service.impl.LayoutSettingsServiceImpl;
import com.restaurant.management.web.request.user.LoginRequest;
import com.restaurant.management.web.request.user.SignUpUserRequest;
import com.restaurant.management.web.request.user.UpdateAccountInfo;
import com.restaurant.management.web.request.user.UserUpdateRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import com.restaurant.management.web.response.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
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

    private AccountUserFacade accountUserFacade;
    private AccountUserMapper accountUserMapper;
    private CompanyAccountUserFacade companyAccountUserFacade;
    private LayoutSettingsService layoutSettingsService;
    private LayoutShortcutService layoutShortcutService;

    @Autowired
    public AccountUserController(AccountUserFacade accountUserFacade,
                                 AccountUserMapper accountUserMapper,
                                 CompanyAccountUserFacade companyAccountUserFacade,
                                 LayoutSettingsServiceImpl layoutSettingsService,
                                 LayoutShortcutService layoutShortcutService) {
        this.accountUserFacade = accountUserFacade;
        this.accountUserMapper = accountUserMapper;
        this.companyAccountUserFacade = companyAccountUserFacade;
        this.layoutSettingsService = layoutSettingsService;
        this.layoutShortcutService = layoutShortcutService;
    }

//    @GetMapping(value = "/me")
//    public @ResponseBody
//    UserSummary getCurrentUserSummary(@CurrentUser UserPrincipal currentUser) {
//        return companyAccountUserFacade.getUserSummary(currentUser);
//    }

    @LogLogin
    @PostMapping(value = "/login", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> authenticateUser2(@Valid @RequestBody LoginRequest loginRequest) {

        JwtAuthenticationResponse jwt = accountUserFacade.authenticateUser(loginRequest);

        UserResponse userResponse = companyAccountUserFacade.getUserDataFromJWT(jwt.getAccessToken());

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
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
    Resource<AccountUserResponse> updateAccountInfo(@CurrentUser UserPrincipal currentUser,
                                                    @Valid @RequestBody UpdateAccountInfo request) {
        AccountUserDto accountUserDto = accountUserFacade.updateAccountInfo(currentUser, request);

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

    @GetMapping(value = "/userData", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    UserResponse getUserData(@CurrentUser UserPrincipal currentUser) {
        return companyAccountUserFacade.getUserData(currentUser);
    }

    @PutMapping(value = "/userData/update", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    UserResponse updateUserData(@CurrentUser UserPrincipal currentUser, @RequestBody UserUpdateRequest userUpdateRequest) {
        return companyAccountUserFacade.updateUserDetails(currentUser, userUpdateRequest);
    }

    @GetMapping(value = "/test", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    String[] show(@CurrentUser UserPrincipal currentUser) {
        return layoutShortcutService.getLayoutShortcuts(currentUser);
    }

}
